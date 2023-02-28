package com.MyRPC.framework.protocol.netty.tcp.proxy;

import com.MyRPC.framework.protocol.netty.tcp.bootstrap.NettyClient;
import com.MyRPC.framework.protocol.netty.tcp.message.ServiceRequestMessage;
import com.MyRPC.framework.protocol.netty.tcp.handler.RequestMessageHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/27 15:58
 * @Version 1.0
 */
@Slf4j
public class ProxyFactoryForNetty {

    private static RequestMessageHandler requestMessageHandler;

    private static ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    static {
        requestMessageHandler = NettyClient.getNettyClientHandler("127.0.0.1", 7777);
    }

    public static <T> T getInstance(Class clazz) throws Exception {
        Object result = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{clazz}, ((proxy, method, args) -> {
            String interfaceName = clazz.getName();
            ServiceRequestMessage requestMessage = new ServiceRequestMessage(interfaceName, method.getName(), method.getParameterTypes(), args, method.getReturnType());
            log.debug(requestMessage.toString());
            //使用requestMessage传调用信息
//            log.debug(requestMessage.toString());
            requestMessageHandler.setRequestMessage(requestMessage);
            return executors.submit(ProxyFactoryForNetty.requestMessageHandler).get();
        }));
        return (T) result;
    }

}
