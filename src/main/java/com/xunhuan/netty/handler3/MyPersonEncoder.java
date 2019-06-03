package com.xunhuan.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器 解决拆包、粘包问题
 * @Auther: tianhuan
 * @Date: 2019/5/31 13:52
 * @Description:
 */
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyPersonEncoder encoder invoked!");

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
