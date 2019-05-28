package com.xunhuan.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author tianhuan
 * @date 2019-05-27 23:01
 **/
public class ByteBufTest02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("你好hello world", Charset.forName("utf-8"));
        /*
            判断是否是一个堆上缓冲
         */
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            System.out.println(new String(array, Charset.forName("utf-8")));
            System.out.println(byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity()); //自动扩容
            int count = byteBuf.readableBytes();
            System.out.println(count);

            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char) byteBuf.getByte(i)); // 单个字节打印，中文会出现乱码
            }

            /*
                指定字符集和长度，中文不乱码
             */
            System.out.println(byteBuf.getCharSequence(0, 6, Charset.forName("utf-8")));
            System.out.println(byteBuf.getCharSequence(6, 8, Charset.forName("utf-8")));
        }

    }
}
