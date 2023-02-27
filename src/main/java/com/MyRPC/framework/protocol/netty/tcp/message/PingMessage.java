package com.MyRPC.framework.protocol.netty.tcp.message;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/27 15:35
 * @Version 1.0
 */
public class PingMessage extends Message implements Serializable {
    @Override
    public int getMessageType() {
        return Message.PingMessage;
    }
}
