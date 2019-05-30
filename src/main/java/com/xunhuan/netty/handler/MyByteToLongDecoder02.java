package com.xunhuan.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * ReplayingDecoder测试
 * 简化decode的编写
 * @Auther: tianhuan
 * @Date: 2019/5/30 14:28
 * @Description:
 */
public class MyByteToLongDecoder02 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode02 invoked!");
        /**
         * 对比MyByteToLongDecoder方法
         */
        out.add(in.readLong());
    }
}
