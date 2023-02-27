package com.MyRPC.framework.protocol.netty.tcp.message;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/27 15:34
 * @Version 1.0
 */
@Data
public class ServiceResponseMessage extends AbstractResponseMessage implements Serializable {
    private Class<?> resultType;
    private Object result;
    private Map<Object, Object> attachment;

    @Override
    public int getMessageType() {
        return Message.ServiceResponseMessage;
    }


}
