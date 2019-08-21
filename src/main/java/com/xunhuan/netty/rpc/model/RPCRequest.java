package com.xunhuan.netty.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPCRequest {
    private String requestId;
    private String type;
    private Object payload;
}
