package com.xunhuan.serialize.java.first;

import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/5 11:22
 * @Description:
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 默认序列号
     */
    public static final long serialVersionUID = 1L;

    private String userName;
    private int userID;

    public UserInfo buildUserName(String userName){
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserID(int userID){
        this.userID = userID;
        return this;
    }

    public byte[] codeCLength(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codeCSpeed(ByteBuffer buffer){
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

}
