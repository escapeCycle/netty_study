package com.xunhuan.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁
 * @author tianhuan
 * @date 2019-04-14 23:34
 **/
public class NioTest10 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        FileLock lock = channel.lock(3, 6, true);

        System.out.println("valid : "+lock.isValid());
        System.out.println("lock isShared : "+lock.isShared());

        lock.release();
        randomAccessFile.close();

    }
}
