package com.xunhuan.netty.rpc.handler;

import com.xunhuan.netty.rpc.model.ExpReponse;
import com.xunhuan.netty.rpc.model.ExpRequest;
import com.xunhuan.netty.rpc.model.MessageOutput;
import io.netty.channel.ChannelHandlerContext;

/**
 * 指数运算处理器
 */
public class ExpRequestHandler implements IMessageHandler<ExpRequest> {
    @Override
    public void handler(ChannelHandlerContext ctx, String requestId, ExpRequest message) {
        int base = message.getBase();
        int exp = message.getExp();

        long start = System.nanoTime();

        int res = 1;

        for (int i = 0; i < exp; i++) {
            res *= base;
        }

        long end  = System.nanoTime();
        ctx.writeAndFlush(new MessageOutput(requestId,"exp_res",new ExpReponse(res,end-start)));
    }
}
