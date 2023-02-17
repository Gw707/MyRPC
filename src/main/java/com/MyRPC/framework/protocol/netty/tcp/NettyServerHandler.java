package com.MyRPC.framework.protocol.netty.tcp;

import com.MyRPC.framework.Invocation;
import com.MyRPC.framework.protocol.register.LocalRegister;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 11:06
 * @Version 1.0
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "===" + ctx.channel().localAddress());

        System.out.println("server is ready...");
        ctx.read();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object info) throws Exception {

        System.out.println( ctx.channel().remoteAddress()+ " 的调用信息为：" + info.toString());
        JSON.config(JSONReader.Feature.SupportClassForName, true);
        Invocation invocation = JSON.parseObject(info.toString(), Invocation.class);

//        System.out.println(invocation.toString());
//        String result = new HelloServiceImpl().sayHello(msg.toString());

        String interfaceName = invocation.getInterfaceName();
        String methodName = invocation.getMethodName();
        Class[] paramTypes = invocation.getParamTypes();
        Object[] params = invocation.getParams();

        //TODO 获取本地注册信息，注册信息为  map：interface-implClass
        Class clazz = LocalRegister.getClass(interfaceName);

        Method method = clazz.getMethod(methodName, paramTypes);


        //调用本地实现方法
        Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), params);

        String res = JSON.toJSONString(result);
        ctx.channel().writeAndFlush(res);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
