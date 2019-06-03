package com.xunhuan.netty.nio.nio2;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/3 18:20
 * @Description:
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}
