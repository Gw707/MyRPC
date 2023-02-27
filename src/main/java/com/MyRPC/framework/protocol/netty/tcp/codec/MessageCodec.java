package com.MyRPC.framework.protocol.netty.tcp.codec;

import com.MyRPC.framework.protocol.netty.tcp.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @Description 自定义协议，即编解码规则，具体规则如下：
 *————————————————————————————————————————————————
 * 魔数：0x7777                              -4字节
 * 版本号：1                                 -1字节
 * 序列化算法：1                             -1字节
 * 业务类型：根据消息类型获取                 -1字节
 * 请求序号：暂时不用                         -4字节
 * 占位符：补成偶数位                         -1字节
 * 消息长度：将消息序列化后的字节数组长度      -4字节
 * 消息内容：根据消息长度读取消息
 * ————————————————————————————————————————————————
 *
 * @Author ygw
 * @Date 2023/2/22 16:23
 * @Version 1.0
 */

@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        byte[] legal = new byte[]{7, 7, 7, 7};
        byteBuf.writeBytes(legal);  //魔数
        byteBuf.writeByte(1);  //版本号

        //TODO 根据配置文件读取选择，暂时默认jdk的序列化方式
        byteBuf.writeByte(1);  //序列化算法

        byteBuf.writeByte(message.getMessageType());  //消息类型

        byteBuf.writeInt(1);  //序列号暂时不用
        byteBuf.writeByte(0xff);  //补成12位协议

        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(aos);
        oos.writeObject(message);

        byte[] content = aos.toByteArray();
        byteBuf.writeInt(content.length);  //将message序列化byte数组后获取长度
        byteBuf.writeBytes(content);  //写入消息内容

//        channelHandlerContext.channel().writeAndFlush(byteBuf);

    }

    @Override
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        Channel channel = channelHandlerContext.channel();

        int legal = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializerMethod = byteBuf.readByte();
        int messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        byteBuf.readByte();
        int contentLength = byteBuf.readInt();
        byte[] content = new byte[contentLength];
        byteBuf.readBytes(content, 0, contentLength);

        //TODO 根据反序列算法解析content,将解析的对象加入list中

        ByteArrayInputStream bis = new ByteArrayInputStream(content);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Message message = (Message) ois.readObject();
//        message.setMessageType(messageType);
//        log.debug(message.toString());
        list.add(message);


    }
}
