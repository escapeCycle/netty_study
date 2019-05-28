package com.xunhuan.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

/**
 * 复合缓冲
 * composite buffer
 *
 * @Auther: tianhuan
 * @Date: 2019/5/28 13:56
 * @Description:
 */
public class ByteBufTest03 {

    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf heapBuf = Unpooled.buffer(10);

        ByteBuf directBuf = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(heapBuf,directBuf);

//        compositeByteBuf.removeComponent(0);

        Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        compositeByteBuf.forEach(System.out::println);
    }
}
