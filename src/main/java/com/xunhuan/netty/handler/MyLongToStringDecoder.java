package com.xunhuan.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * MessageToMessageDecoder
 * 内部消息之间的编码转换
 *
 * @Auther: tianhuan
 * @Date: 2019/5/30 15:25
 * @Description:
 */
public class MyLongToStringDecoder extends MessageToMessageDecoder<Long> {


    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("MyLongToStringDecoder decode invoked!");
        out.add(String.valueOf(msg));
    }
}