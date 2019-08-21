package com.xunhuan.netty.rpc.model;


import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageInput {

    private String type;
    private String requestId;
    private String payload;

    // 因为我们想直接拿到对象，所以要提供对象的类型参数
    public <T> T getPayload(Class<T> clazz) {
        if (payload == null) {
            return null;
        }
        return JSON.parseObject(payload, clazz);
    }
}
