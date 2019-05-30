package com.xunhuan.netty.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author tianhuan
 * @date 2019-02-14 14:57
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];  //目标数组不能大于源的长度
        msg.readBytes(buffer);

        String message = new String(buffer, Charset.forName("utf-8"));

        System.out.println("服务端接收到的消息内容: " + message);
        System.out.println("服务端接收到的消息数量: " + (++this.count));

        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
