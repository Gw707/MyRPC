package com.MyRPC.framework.protocol.netty.tcp.handler;

import com.MyRPC.framework.protocol.netty.tcp.message.ServiceRequestMessage;
import com.MyRPC.framework.protocol.netty.tcp.message.ServiceResponseMessage;
import com.MyRPC.framework.register.LocalRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 11:06
 * @Version 1.0
 */
public class ResponseMessageHandler extends SimpleChannelInboundHandler<ServiceRequestMessage> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "===" + ctx.channel().localAddress());

        System.out.println("server is ready...");
        ctx.read();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServiceRequestMessage serviceRequestMessage) throws Exception {
//        System.out.println( ctx.channel().remoteAddress()+ " 的调用信息为：" + serviceRequestMessage.toString());

        String interfaceName = serviceRequestMessage.getInterfaceName();
        String methodName = serviceRequestMessage.getMethodName();
        Class[] paramTypes = serviceRequestMessage.getParamTypes();
        Object[] params = serviceRequestMessage.getParams();

        //TODO 获取本地注册信息，注册信息为  map：interface-implClass
        Class clazz = LocalRegister.getClass(interfaceName);

        Method method = clazz.getMethod(methodName, paramTypes);


        //调用本地实现方法
        Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), params);

        ServiceResponseMessage responseMessage = new ServiceResponseMessage();
        responseMessage.setResultType(method.getReturnType());
        responseMessage.setResult(result);
        ctx.channel().writeAndFlush(responseMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
