package com.xunhuan.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * 内存映射 由操作系统来完成内存到磁盘的操作 使用MappedByteBuffer(位于堆外内存),
 * 我们只需要操作内存中的内容,操作系统会帮助我们完成内存到磁盘的操作
 * @author tianhuan
 * @date 2019-04-14 23:16
 **/
public class NioTest9 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte) 'a');
        map.put(3, (byte) 'b');
        randomAccessFile.close();
    }
}
