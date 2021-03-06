package com.xunhuan.netty.secondsample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author tianhuan
 * @date 2019-02-14 14:57
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + ", " + msg);

        ctx.channel().writeAndFlush("from server: " +UUID.randomUUID());
        ctx.writeAndFlush("hello"); // 从当前handler的下一个开始流动，都是短路的方法，之前的不管
        ctx.channel().writeAndFlush("hello"); // 出栈处理器的第一个开始流动

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
