package com.xunhuan.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 自定义解码器  解决拆包、粘包问题
 * @Auther: tianhuan
 * @Date: 2019/5/31 13:48
 * @Description:
 */
public class MyPersonDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyPersonDecoder decode invoked!");
        /**
         * 关键: 此处解码器需要读对应长度的内容到byte数组中,以便对一条完整的消息进行封装
         */
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setContent(content);
        personProtocol.setLength(length);

        out.add(personProtocol);
    }
}
