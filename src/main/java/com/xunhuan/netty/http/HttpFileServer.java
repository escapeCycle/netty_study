package com.xunhuan.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class HttpFileServer {

    public static final String DEFAULT_URL = "src/main/java/com/xunhuan/";

    public static void main(String[] args) {


        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer(DEFAULT_URL));

            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            System.out.println("HTTP 文件目录服务器启动成功，网址是 ：http://localhost:8899/" + DEFAULT_URL);
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
