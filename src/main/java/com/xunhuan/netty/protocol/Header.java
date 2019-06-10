package com.xunhuan.netty.protocol;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/10 17:33
 * @Description:
 */
@Data
@ToString
public class Header {

    private int crcCode = 0xabef0101;
    /**
     * 消息长度
     */
    private int length;
    /**
     * 会话ID
     */
    private long sessionID;
    /**
     * 消息类型
     */
    private byte type;

    /**
     * 消息优先级
     */
    private byte priority;

    /**
     * 附件
     */
    private Map<String,Object> attachment = new HashMap<String,Object>();

}
