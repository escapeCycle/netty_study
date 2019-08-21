package com.xunhuan.netty.rpc.handler;

import com.google.common.base.Charsets;
import com.xunhuan.netty.rpc.model.MessageInput;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageDecoder extends ReplayingDecoder<MessageInput> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String requestId = readStr(in);
        String type = readStr(in);
        String payload = readStr(in);
        out.add(new MessageInput(type,requestId,payload));
    }

    private String readStr(ByteBuf in){
        int len = in.readInt();

        if(len < 0 || len > (1 << 20)){
            throw new DecoderException("string to long ,len: " + len);
        }

        byte[] bytes = new byte[len];

        in.readBytes(bytes);
        return new String(bytes, Charsets.UTF_8);
    }
}
