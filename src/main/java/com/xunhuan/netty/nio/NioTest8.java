package com.xunhuan.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tianhuan
 * @date 2019-04-14 22:36
 **/
public class NioTest8 {

    public static void main(String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputStreamChannel = inputStream.getChannel();

        FileChannel outputStreamChannel = outputStream.getChannel();

        /**
         * allocateDirect
         */
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        while (true){
            byteBuffer.clear();
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
