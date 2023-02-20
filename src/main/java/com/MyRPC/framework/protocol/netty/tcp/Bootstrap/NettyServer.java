package com.MyRPC.framework.protocol.netty.tcp.Bootstrap;

import com.MyRPC.framework.protocol.netty.tcp.Handler.IdleEventHandler;
import com.MyRPC.framework.protocol.netty.tcp.Handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

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
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new ByteArrayDecoder());
                            pipeline.addLast(new ByteArrayEncoder());
                            pipeline.addLast(new NettyServerHandler());
                            //设置可以接受的TimeOut时间
                            pipeline.addLast(new IdleStateHandler(5, 5, 5, TimeUnit.SECONDS));
                            pipeline.addLast(new IdleEventHandler());
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
