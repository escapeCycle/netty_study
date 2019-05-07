package com.xunhuan.netty.nio;

import java.nio.ByteBuffer;

/**
 * Slice Buffer 原有Buffer的快照，数据是一份，只不过position\limit和mark是独享的，
 * 而且任意一方数据修改，另一方都会被修改
 * @author tianhuan
 * @date 2019-02-21 16:42
 **/
public class NioTest6 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.position(2);
        byteBuffer.limit(10);

        ByteBuffer sl = byteBuffer.slice();

        for (int i = 0; i < sl.capacity(); i++) {
            byte b = sl.get(i);
            b *= 2;
            sl.put(i, b);
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());

        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
    }
}
