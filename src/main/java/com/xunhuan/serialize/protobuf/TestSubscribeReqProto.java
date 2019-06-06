package com.xunhuan.serialize.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * proto 编码解码测试
 *
 * @Auther: tianhuan
 * @Date: 2019/6/6 16:07
 * @Description:
 */
public class TestSubscribeReqProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }


    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("tian");
        builder.setProductName("Netty Book");
        List<String> adress = new ArrayList<>();
        adress.add("Beijing");
        adress.add("Tianjin");
        adress.add("Shanghai");
        builder.addAllAddress(adress);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : " + req.toString());

        byte[] encode = encode(req);
        System.out.println(encode.length);
        System.out.println(new String(encode,Charset.forName("utf-8")));

//        SubscribeReqProto.SubscribeReq reqT = decode(encode(req));
//        System.out.println("After decode : " + req.toString());
//        System.out.println("Assert equal : " + req.equals(reqT));
    }


}
