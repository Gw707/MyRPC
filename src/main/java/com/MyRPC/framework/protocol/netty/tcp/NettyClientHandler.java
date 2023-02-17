package com.MyRPC.framework.protocol.netty.tcp;

import com.MyRPC.framework.Invocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Callable;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 11:07
 * @Version 1.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;

    private static String result;

    private String info;

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //得到远程调用返回的数据，进行打印
        result = "调用成功，返回的结果为：" + msg.toString();
        notify();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //在channel建立完成后进行远程过程调用
        context = ctx;
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("HelloService#Hello#ygw".getBytes()));
        System.out.println(ctx.channel().remoteAddress() + "===" + ctx.channel().localAddress());
        ctx.read();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    /**
     * 被代理对象调用，发送请求给服务器，进入wait状态，当调用结果返回时使用notify唤醒call
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        //将Invocation信息传给服务器
        context.channel().writeAndFlush(info);
        wait();
        return result;
    }

}
