package com.xunhuan.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 入栈 将字节流转换成Long
 * @Auther: tianhuan
 * @Date: 2019/5/30 11:43
 * @Description:
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked!");
        System.out.println(in.readableBytes());

        if(in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
