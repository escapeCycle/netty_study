package com.xunhuan.netty.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageOutput {

    private String requestId;
    private String type;
    private Object payload;
}
