package com.xunhuan.netty.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tianhuan
 * @date 2019-02-20 15:56
 **/
public class NioTest3 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] bytes = "hello world welcome,nihao".getBytes();
        for (int i = 0;i < bytes.length ; ++i){
            byteBuffer.put(bytes[i]);
        }

        byteBuffer.put(0,(byte)'W').put((byte) ' ').put((byte)'a');
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
