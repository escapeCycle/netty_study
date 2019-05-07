package com.xunhuan.netty.fourthsample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author tianhuan
 * @date 2019-02-17 15:21
 **/
public class MyServerInitialiser extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IdleStateHandler(8, 10, 15, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandle());

    }
}
