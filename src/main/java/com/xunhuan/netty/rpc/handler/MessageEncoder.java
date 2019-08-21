package com.xunhuan.netty.rpc.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.xunhuan.netty.rpc.model.MessageOutput;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<MessageOutput> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageOutput msg, List<Object> out) throws Exception {

        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer();
        writeStr(byteBuf,msg.getRequestId());
        writeStr(byteBuf,msg.getType());
        writeStr(byteBuf, JSON.toJSONString(msg.getPayload()));
        out.add(byteBuf);
    }

    private void writeStr(ByteBuf buf,String str){
        buf.writeInt(str.length());
        buf.writeBytes(str.getBytes(Charsets.UTF_8));
    }
}
