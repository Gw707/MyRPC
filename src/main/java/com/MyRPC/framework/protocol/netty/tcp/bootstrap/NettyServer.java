package com.MyRPC.framework.protocol.netty.tcp.bootstrap;

import com.MyRPC.framework.protocol.netty.tcp.codec.MessageCodec;
import com.MyRPC.framework.protocol.netty.tcp.codec.MyFrameDecoder;
import com.MyRPC.framework.protocol.netty.tcp.handler.HeartBeatHandler;
import com.MyRPC.framework.protocol.netty.tcp.handler.PingMessageHandler;
import com.MyRPC.framework.protocol.netty.tcp.handler.ResponseMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Description 调用startServer0()方法开启服务器
 * @Author ygw
 * @Date 2023/2/14 11:06
 * @Version 1.0
 */
public class NettyServer {

    public void startServer0() throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new MyFrameDecoder());
                            pipeline.addLast(new LoggingHandler());
                            pipeline.addLast(new MessageCodec());
                            pipeline.addLast(new PingMessageHandler());
                            pipeline.addLast(new IdleStateHandler(0, 0, 5));
                            pipeline.addLast(new HeartBeatHandler());
                            pipeline.addLast(new ResponseMessageHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 7777).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
