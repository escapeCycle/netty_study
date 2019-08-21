package com.xunhuan.netty.rpc.handler;

import com.xunhuan.netty.rpc.model.MessageInput;
import io.netty.channel.ChannelHandlerContext;

public class DefaultHandler implements IMessageHandler<MessageInput> {
    @Override
    public void handler(ChannelHandlerContext ctx, String requestId, MessageInput message) {
        System.out.println("unrecognized message type: " + message.getType() + " comes");
    }
}