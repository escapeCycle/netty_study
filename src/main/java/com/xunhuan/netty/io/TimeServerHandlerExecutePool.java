package com.xunhuan.netty.io;

import java.util.concurrent.*;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/3 14:56
 * @Description:
 */
public class TimeServerHandlerExecutePool implements Executor {

    private ExecutorService executor;


    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxPoolSize,
                120L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    @Override
    public void execute(Runnable task){
        executor.execute(task);
    }
}
