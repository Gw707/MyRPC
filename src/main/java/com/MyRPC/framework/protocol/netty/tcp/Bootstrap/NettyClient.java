package com.MyRPC.framework.protocol.netty.tcp.Bootstrap;


import com.MyRPC.framework.protocol.netty.tcp.codec.MessageCodec;
import com.MyRPC.framework.protocol.netty.tcp.codec.MyFrameDecoder;
import com.MyRPC.framework.protocol.netty.tcp.Handler.HeartBeatHandler;
import com.MyRPC.framework.protocol.netty.tcp.Handler.RequestMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * 此处的channel使用单例模式进行创建，同时还需保证多线程下的并发安全
 *
 * @Author ygw
 * @Date 2023/2/14 11:07
 * @Version 1.0
 */
@Slf4j
public class NettyClient {

    private static RequestMessageHandler requestMessageHandler;
    private static final Object LOCK = new Object();

    public static RequestMessageHandler getNettyClientHandler(String host, int port) {
        if(requestMessageHandler != null){
            return requestMessageHandler;
        }
        synchronized (LOCK){
            if (requestMessageHandler != null){
                return requestMessageHandler;
            }
            try {
                initClient(host, port);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("客户端初始化失败");
            }
            return requestMessageHandler;
        }

    }

    private static void initClient(String host, int port) throws InterruptedException {
        requestMessageHandler = new RequestMessageHandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new MyFrameDecoder());
                        pipeline.addLast(new LoggingHandler());
                        pipeline.addLast(new MessageCodec());
                        pipeline.addLast(new IdleStateHandler(0, 3, 0));
                        pipeline.addLast(new HeartBeatHandler());
                        pipeline.addLast(requestMessageHandler);
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        channelFuture.channel().closeFuture();
    }

}
