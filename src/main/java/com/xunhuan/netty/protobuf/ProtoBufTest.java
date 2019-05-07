package com.xunhuan.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author tianhuan
 * @date 2019-02-19 23:53
 **/
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Stdent stdent = DataInfo.Stdent.newBuilder()
                .setName("张小三")
                .setAge(18)
                .setAddress("北京")
                .build();

        byte[] studentToByteArry = stdent.toByteArray();

        DataInfo.Stdent stdent2 = DataInfo.Stdent.parseFrom(studentToByteArry);

        System.out.println(stdent2);
        System.out.println(stdent2.getName());
        System.out.println(stdent2.getAge());
        System.out.println(stdent2.getAddress());

    }
}
