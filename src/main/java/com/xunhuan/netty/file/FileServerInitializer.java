package com.xunhuan.netty.file;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/10 15:43
 * @Description:
 */
public class FileServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8),
                new LineBasedFrameDecoder(1024),
                new StringDecoder(CharsetUtil.UTF_8),
                new FileServerHandler());
    }
}
