package com.MyRPC.framework.protocol.netty.tcp.handler;

import com.MyRPC.framework.protocol.netty.tcp.message.ServiceRequestMessage;
import com.MyRPC.framework.protocol.netty.tcp.message.ServiceResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 11:07
 * @Version 1.0
 */

@Slf4j
public class RequestMessageHandler extends SimpleChannelInboundHandler<ServiceResponseMessage> implements Callable {

    private ChannelHandlerContext context;

    private static Object result;

    private ServiceRequestMessage requestMessage;

    public void setRequestMessage(ServiceRequestMessage requestMessage) {
        this.requestMessage = requestMessage;
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, ServiceResponseMessage serviceResponseMessage) throws Exception {
        //得到远程调用返回的数据，进行打印
        result = serviceResponseMessage.getResult();
//        System.out.println(result.toString());
        notify();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client is ready...");
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
        context.channel().writeAndFlush(requestMessage);
        wait();
        return result;
    }


}
