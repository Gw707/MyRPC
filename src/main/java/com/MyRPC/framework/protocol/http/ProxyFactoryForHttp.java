package com.MyRPC.framework.protocol.http;

import com.MyRPC.framework.Invocation;
import com.MyRPC.framework.LoadBalance;
import com.MyRPC.framework.protocol.http.HttpClient;
import com.MyRPC.framework.protocol.register.RemoteRegister;
import com.MyRPC.framework.protocol.register.URL;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 15:32
 * @Version 1.0
 */
public class ProxyFactoryForHttp {

    public static <T> T getInstance(Class interfaceClass){

        //TODO 此处增加动态代理的选择：jdk、cglib

        Object instance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //执行动态代理的逻辑
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);

                HttpClient httpClient = new HttpClient();

                //TODO 根据具体的服务选择指定的url，从远程的注册中心获取
                List<URL> list = RemoteRegister.get(interfaceClass.getName());

                //TODO 此处可以选择轮询的策略
                URL url = LoadBalance.RandomURL(list);

                //TODO send返回结果为object更好
                String result = httpClient.send(url, invocation);

                return result;
            }
        });

        return (T) instance;
    }

}
