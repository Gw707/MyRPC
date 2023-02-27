package com.MyRPC.framework.protocol.netty.tcp.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {

    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    public static final int PingMessage = 0;
    public static final int PongMessage = 1;
    public static final int ServiceRequestMessage = 2;
    public static final int ServiceResponseMessage = 3;
    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(PingMessage, PingMessage.class);
        messageClasses.put(PongMessage, PongMessage.class);
        messageClasses.put(ServiceRequestMessage, ServiceRequestMessage.class);
        messageClasses.put(ServiceResponseMessage, ServiceResponseMessage.class);
    }
}
