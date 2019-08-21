package com.xunhuan.netty.rpc.handler;

import com.xunhuan.netty.rpc.model.MessageOutput;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 斐波那契处理器
 */
public class FibRequestHandler implements IMessageHandler<Integer> {

    private List<Long> fibs = new ArrayList<>();

    {
        fibs.add(1L);
        fibs.add(1L);
    }

    @Override
    public void handler(ChannelHandlerContext ctx, String requestId, Integer message) {

        for (int i = fibs.size(); i < message + 1; i++) {
            long value = fibs.get(i-1) + fibs.get(i-2);
            fibs.add(value);
        }
        //输出
        ctx.writeAndFlush(new MessageOutput(requestId,"fib_type",fibs.get(message)));
    }
}
