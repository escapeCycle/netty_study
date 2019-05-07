package com.xunhuan.netty.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author tianhuan
 * @date 2019-02-20 14:35
 **/
public class NioTest1 {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        for(int i = 0;i < 5;++i){
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber); //写 输出流
        }
        System.out.println("before: " + buffer.limit());

        buffer.flip(); // 翻转 之后变成输入流
        System.out.println("after: " + buffer.limit());
        while (buffer.hasRemaining()){
            System.out.println(buffer.get()); // 读 输入流
        }
    }
}
