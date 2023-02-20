package com.MyRPC.framework.protocol.netty.tcp.Bootstrap;


import com.MyRPC.framework.Invocation;
import com.MyRPC.framework.protocol.netty.tcp.Handler.NettyClientHandler;
import com.alibaba.fastjson2.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 11:07
 * @Version 1.0
 */
public class NettyClient {

    private static ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler nettyClientHandler;

    public static <T> T getInstance(Class clazz){

        Object instance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{clazz}, ((proxy, method, args) -> {
            String interfaceName = clazz.getName();


            if (nettyClientHandler == null) {
                //TODO 从远程的注册中心获取服务的ip：port，然后进行初始化
                initClient("127.0.0.1", 7777);
            }

            //TODO JSON传Invocation数据
            Invocation invocation = new Invocation(interfaceName, method.getName(), method.getParameterTypes(), args);
            System.out.println(invocation.toString());
            String info = JSON.toJSONString(invocation);
            nettyClientHandler.setInfo(info);
            return executors.submit(nettyClientHandler).get();
        }));

        return (T)instance;
    }
    public static void initClient(String host, int port) throws InterruptedException {
        nettyClientHandler = new NettyClientHandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());

                        pipeline.addLast(nettyClientHandler);
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7777).sync();

    }

}
