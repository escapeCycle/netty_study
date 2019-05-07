package com.xunhuan.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * Channel read方法的返回值情况如下：
 * n 有数据的时候返回读取到的字节数。
 * 0 没有数据并且没有达到流的末端时返回0。
 * -1 当达到流末端的时候(也就是到limit的时候)返回-1。
 *
 * @author tianhuan
 * @date 2019-02-21 15:19
 **/
public class NioTest4 {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputStreamChannel = inputStream.getChannel();

        FileChannel outputStreamChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); // 如果值为4,那么每次读四个字节

        while (true){
            byteBuffer.clear(); //如果注释掉该行代码会发生什么情况
            int read = inputStreamChannel.read(byteBuffer);

            System.out.println("read: " + read);

            if(read == -1){
                break;
            }

            byteBuffer.flip();

            outputStreamChannel.write(byteBuffer);
        }

        inputStream.close();
        outputStream.close();
    }
}
