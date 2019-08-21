package com.xunhuan.netty.rpc.handler;

import io.netty.channel.ChannelHandlerContext;

public interface IMessageHandler<T> {

    void handler(ChannelHandlerContext ctx, String requestId, T message);
}
