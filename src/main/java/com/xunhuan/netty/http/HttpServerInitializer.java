package com.xunhuan.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final String uri;

    public HttpServerInitializer(String uri) {
        this.uri = uri;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("http-decode",new HttpRequestDecoder());
        pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));
        pipeline.addLast("http-encoder",new HttpResponseEncoder());
        pipeline.addLast("http-chunker",new ChunkedWriteHandler());
        pipeline.addLast("fileServerHandler",new HttpFileServerHandler(uri));
    }
}
