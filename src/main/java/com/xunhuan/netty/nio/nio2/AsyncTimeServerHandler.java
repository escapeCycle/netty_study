package com.xunhuan.netty.nio.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/3 18:20
 * @Description:
 */
public class AsyncTimeServerHandler implements Runnable {

    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void doAccept() {
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
