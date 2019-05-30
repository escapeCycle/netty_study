package com.xunhuan.netty.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author tianhuan
 * @date 2019-02-14 16:10
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("客户端接收到的消息内容: " + message);
        System.out.println("客户端接收到的消息数量:  " + (++this.count));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*
            此处会产生粘包,可能会把10次write组成一次发送给服务端,服务端接收到的就是一条数据 send from client  send from client  send from client。。。。
        */
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("send from client ", Charset.forName("utf-8"));
            ctx.writeAndFlush(byteBuf);
        }
    }


}
