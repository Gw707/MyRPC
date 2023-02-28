package com.MyRPC.framework.protocol.netty.tcp.handler;

import com.MyRPC.framework.protocol.netty.tcp.message.PingMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description 服务器用于接收心跳数据包的处理器
 * @Author ygw
 * @Date 2023/2/27 21:10
 * @Version 1.0
 */
public class PingMessageHandler extends SimpleChannelInboundHandler<PingMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PingMessage pingMessage) throws Exception {
        //channelHandlerContext.channel().writeAndFlush(new PongMessage());
    }
}
