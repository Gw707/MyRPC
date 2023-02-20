package com.MyRPC.framework.protocol.netty.tcp.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class IdleEventHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

        if (idleStateEvent.state().equals(IdleState.READER_IDLE)){
            System.out.println("发生了读空闲，此时对channel进行检测");
            checkAndReload(ctx);
        }
    }

    /**
     * 在发生读空闲后对channel进行检测
     * @param ctx
     */
    private void checkAndReload(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        if(channel.isActive()){
            //仍然保持连接，直接返回
        }
        //如果断开连接了，客户端则尝试重新连接5次（可设置：失败后重试次数）
        //TODO 如果是客户端，尝试连接成功直接返回，失败返回异常信息说明

        //TODO 如果是服务端，尝试连接成功直接返回，失败则将远程注册的服务状态改为不可用

    }
}
