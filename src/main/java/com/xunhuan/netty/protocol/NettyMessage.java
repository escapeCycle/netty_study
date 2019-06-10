package com.xunhuan.netty.protocol;

import lombok.Data;
import lombok.ToString;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/10 17:32
 * @Description:
 */
@Data
@ToString
public class NettyMessage {
    private Header header;
    private Object body;

}
