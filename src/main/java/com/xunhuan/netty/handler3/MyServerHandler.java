package com.xunhuan.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/31 13:55
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到的数据:");
        System.out.println("长度: "+length);
        System.out.println("内容: "+new String(content,Charset.forName("utf-8")));
        System.out.println("服务端接收到的消息数量: "+ (++this.count));


        String message = UUID.randomUUID().toString();

        int respLength = message.getBytes("utf-8").length;
        byte[] respContent = message.getBytes("utf-8");

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(respLength);
        personProtocol.setContent(respContent);
        ctx.writeAndFlush(personProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
