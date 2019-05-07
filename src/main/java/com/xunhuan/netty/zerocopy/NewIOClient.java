package com.xunhuan.netty.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/7 17:50
 * @Description:
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "D:\\下载文件\\CentOS-7-x86_64-Minimal-1810.iso";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        /**
         * 零拷贝关键
         */
        long position = 0;
        long size = fileChannel.size();
        long total = 0;
        while (position < size){
            long transferCount = fileChannel.transferTo(position, fileChannel.size(), socketChannel);
            if (transferCount <= 0) {
                break;
            }
            total += transferCount;
            position += transferCount;
        }
        System.out.println("发送的总字节数: " + total + ", 耗时: " + (System.currentTimeMillis() - startTime));

        fileChannel.close();
    }
}
