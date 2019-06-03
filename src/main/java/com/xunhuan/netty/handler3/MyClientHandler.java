package com.xunhuan.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/31 14:01
 * @Description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到的消息: ");
        System.out.println("长度" + length);
        System.out.println("内容" + new String(content, Charset.forName("utf-8")));
        System.out.println("数量: " + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String message = "send from client ";

            byte[] content = message.getBytes("utf-8");
            int length = message.getBytes("utf-8").length;

            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setLength(length);
            personProtocol.setContent(content);
            ctx.writeAndFlush(personProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
