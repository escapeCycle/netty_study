package com.xunhuan.netty.secondsample;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/8 18:25
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        int max = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(max);
    }
}
