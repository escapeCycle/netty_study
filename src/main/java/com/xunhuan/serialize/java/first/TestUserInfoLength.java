package com.xunhuan.serialize.java.first;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 序列化测试(码流大小)
 *
 * 测试结果 : 采用JDK序列化机制编码后的二进制数据大小是二进制编码的5倍多
 * @Auther: tianhuan
 * @Date: 2019/6/5 11:33
 * @Description:
 */
public class TestUserInfoLength {

    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is : " + b.length);
        bos.close();
        System.out.println("---------------------------------");
        System.out.println("The byte array serializable length is : " + info.codeCLength().length);
    }
}
