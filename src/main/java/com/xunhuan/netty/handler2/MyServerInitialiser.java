package com.xunhuan.netty.handler2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author tianhuan
 * @date 2019-02-14 14:51
 **/
public class MyServerInitialiser extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
        * 每当有一个客户端连接过来的时候,MyServerInitialiser不会重新new,但是initChannel就会重新调用一次,MyServerHandler也就会重新new一次
        *
        * 每一个客户端连接都会有一个自己的管道
        */
        pipeline.addLast(new MyServerHandler());
    }
}
