package com.xunhuan.netty.nio;

import java.nio.ByteBuffer;

/**
 * 只读Buffer
 *
 * @author tianhuan
 * @date 2019-02-21 16:57
 **/
public class NioTest7 {
    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        System.out.println(byteBuffer.getClass());
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        /*
            只读Buffer
        */
        ByteBuffer asReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(asReadOnlyBuffer.getClass());

        asReadOnlyBuffer.position(0);
        asReadOnlyBuffer.put((byte) 3);
    }
}
