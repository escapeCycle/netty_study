package com.xunhuan.netty.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tianhuan
 * @date 2019-02-20 15:49
 **/
public class NioTest2 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        fileChannel.read(byteBuffer);

        /*
            翻转输入输出流
         */
        byteBuffer.flip();
        while (byteBuffer.remaining() > 0){
            byte b = byteBuffer.get();
            System.out.println("Character: "+(char)b);
        }

        fileInputStream.close();

    }
}
