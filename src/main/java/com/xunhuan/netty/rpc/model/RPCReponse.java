package com.xunhuan.netty.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPCReponse {
    private String requestId;
    private String type;
    private Object payload;
}
