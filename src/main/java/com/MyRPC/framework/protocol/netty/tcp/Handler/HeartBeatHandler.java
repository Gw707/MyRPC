package com.MyRPC.framework.protocol.netty.tcp.Handler;

import com.MyRPC.framework.protocol.netty.tcp.message.PingMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/27 20:44
 * @Version 1.0
 */
@Slf4j
public class HeartBeatHandler extends ChannelDuplexHandler {

    /**
     * 处理空闲事件（相当于一个定时事件）
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        IdleStateEvent event = (IdleStateEvent) evt;
        /**
         * 客户端--发生了写空闲，说明客户端未及时进行调用，发送心跳包保持连接状态
         */
        if (event.state() == IdleState.WRITER_IDLE){
            PingMessage pingMessage = new PingMessage();
            log.debug("发送了一个心跳数据包");
            ctx.writeAndFlush(pingMessage);
        }

        /**
         * 服务端--发生了读空闲，也没有收到心跳包，说明用户断开连接，此时关闭channel释放资源
         */
        if(event.state() == IdleState.READER_IDLE){
            log.debug("客户端断开连接");
            ctx.channel().close();
        }
    }
}
