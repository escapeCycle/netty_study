package com.xunhuan.netty.rpc;

import com.xunhuan.netty.rpc.client.RpcClient;
import com.xunhuan.netty.rpc.model.ExpReponse;
import com.xunhuan.netty.rpc.model.ExpRequest;

public class DemoClient {

    private RpcClient rpcClient;

    public DemoClient(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
        this.rpcClient.rpc("fib_res", Long.class).rpc("exp_res", ExpReponse.class);
    }

    public Long fib(int n) {
        return (Long) rpcClient.send("fib", n);
    }

    public ExpReponse exp(int base, int exp) {
        return (ExpReponse) rpcClient.send("exp", new ExpRequest(base, exp));
    }

    public static void main(String[] args) {
        RpcClient client = new RpcClient("localhost", 8888);
        DemoClient demo = new DemoClient(client);
        for (int i = 0; i < 20; i++) {
            System.out.printf("fib(%d) = %d\n", i, demo.fib(i));
        }
        for (int i = 0; i < 20; i++) {
            ExpReponse res = demo.exp(2, i);
            System.out.printf("exp2(%d) = %d cost=%dns\n", i, res.getValue(), res.getCostInNanos());
        }

        demo.rpcClient.close();
    }
}
