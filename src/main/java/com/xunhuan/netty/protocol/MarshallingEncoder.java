package com.xunhuan.netty.protocol;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @Auther: tianhuan
 * @Date: 2019/6/10 17:52
 * @Description:
 */
public class MarshallingEncoder {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;
    public MarshallingEncoder() throws IOException{
        marshaller = MarshallingCodecFactory.buildMarshalling();

    }

    protected void encode(Object msg, ByteBuf out) throws IOException {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            out.setInt(lengthPos,out.writerIndex() - lengthPos - 4);
        }finally {
            marshaller.close();
        }
    }
}
