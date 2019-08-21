package com.xunhuan.netty.rpc.handler;

import com.xunhuan.netty.rpc.model.MessageInput;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class MessageCollector extends ChannelInboundHandlerAdapter {

    private ThreadPoolExecutor executor;

    public MessageCollector(int workerThreads) {
        //  业务队列最大1000，防止堆积
        // 如果子线程处理不过来，io线程也会加入处理业务逻辑(callerRunsPolicy)
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);

        //命名线程
        ThreadFactory factory = new ThreadFactory() {

            AtomicInteger seq = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("rpc-" + seq.getAndIncrement());
                return t;
            }
        };

        //闲置时间超过30s线程自动销毁
        this.executor = new ThreadPoolExecutor(1, workerThreads, 30,
                TimeUnit.SECONDS, queue, factory, new ThreadPoolExecutor.CallerRunsPolicy());

    }

    public void closeGracefully() {
        //先通知，再等待，最后强制关闭
        this.executor.shutdown();

        try {
            this.executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {

        }
        this.executor.shutdownNow();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("message come");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("message leave");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageInput) {
            System.out.println("read a message ");

            this.executor.execute(() -> {
                handlerMessage(ctx, (MessageInput) msg);
            });
        }

    }


    private void handlerMessage(ChannelHandlerContext ctx, MessageInput input) {
        //业务逻辑处理
        Class<?> clazz = MessageRegistry.get(input.getType());
        if (clazz == null) {
            //未注册的消息用默认的处理器处理
            MessageHandlers.defaultHandler.handler(ctx, input.getRequestId(), input);
            return;
        }

        Object payload = input.getPayload(clazz);
        @SuppressWarnings("unchecked")
        IMessageHandler<Object> handler = (IMessageHandler<Object>) MessageHandlers.get(input.getType());

        if (handler != null) {
            handler.handler(ctx, input.getRequestId(), payload);
        } else {
            MessageHandlers.defaultHandler.handler(ctx, input.getRequestId(), input);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 此处可能因为客户端机器突发重启
        // 也可能是客户端链接闲置时间超时，后面的ReadTimeoutHandler抛出来的异常
        // 也可能是消息协议错误，序列化异常
        // etc.
        // 不管它，链接统统关闭，反正客户端具备重连机制
        System.out.println("connection error");
        cause.printStackTrace();
        ctx.close();
    }

}
