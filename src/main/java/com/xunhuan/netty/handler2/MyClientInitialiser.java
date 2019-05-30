package com.xunhuan.netty.handler2;

import com.xunhuan.netty.handler.MyByteToLongDecoder02;
import com.xunhuan.netty.handler.MyLongToByteEncoder;
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

        pipeline.addLast(new MyClientHandler());
    }
}
