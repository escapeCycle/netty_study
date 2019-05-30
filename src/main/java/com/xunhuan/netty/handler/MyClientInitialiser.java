package com.xunhuan.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @author tianhuan
 * @date 2019-02-14 16:09
 **/
public class MyClientInitialiser extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyByteToLongDecoder02());
        pipeline.addLast(new MyLongToByteEncoder());
        pipeline.addLast(new MyClientHandler());
    }
}
