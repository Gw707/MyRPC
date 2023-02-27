package com.MyRPC.framework.protocol.netty.tcp.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/27 15:34
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestMessage extends Message implements Serializable {
    //接口名、方法名、参数类型列表、参数值列表、版本号

    private String interfaceName;

    private String methodName;

    private Class[] paramTypes;

    private Object[] params;

    private Class<?> resultType;


    @Override
    public int getMessageType() {
        return Message.ServiceRequestMessage;
    }


}
