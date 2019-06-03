package com.xunhuan.netty.nio.nio2;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/3 18:47
 * @Description:
 */
public class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer> {
    @Override
    public void completed(Integer result, ByteBuffer attachment) {

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

    }
}
