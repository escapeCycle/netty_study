package com.xunhuan.netty.rpc;

import com.xunhuan.netty.rpc.handler.ExpRequestHandler;
import com.xunhuan.netty.rpc.handler.FibRequestHandler;
import com.xunhuan.netty.rpc.model.ExpRequest;
import com.xunhuan.netty.rpc.server.RpcServer;

public class DemoServer {

    public static void main(String[] args) throws InterruptedException {
        RpcServer rpcServer = new RpcServer("localhost", 8888, 2, 16);

        rpcServer.service("fib",Integer.class,new FibRequestHandler());
        rpcServer.service("exp", ExpRequest.class,new ExpRequestHandler());
        rpcServer.start();
    }
}
