package com.xunhuan.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/27 14:50
 * @Description:
 */
public class ByteBufTest01 {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
//        while (buffer.isReadable()){
//            System.out.println(buffer.readByte());
//        }

    }
}
