package com.xunhuan.netty.rpc.exception;

public class RPCException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public RPCException(String message,Throwable cause){
        super(message,cause);
    }

    public RPCException(String message){
        super(message);
    }
    public RPCException(Throwable cause){
        super(cause);
    }
}
