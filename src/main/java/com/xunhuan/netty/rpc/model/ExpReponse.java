package com.xunhuan.netty.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpReponse {

    private long value;
    private long costInNanos;

}
