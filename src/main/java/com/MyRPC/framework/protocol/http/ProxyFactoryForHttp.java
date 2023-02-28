package com.MyRPC.framework.protocol.http;

import com.MyRPC.framework.LoadBalance.CircleBalance;
import com.MyRPC.framework.LoadBalance.RandomBalance;
import com.MyRPC.framework.protocol.netty.tcp.bootstrap.Config;
import com.MyRPC.framework.register.RemoteRegister;
import com.MyRPC.framework.register.URL;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Random;

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
                URL url = null;
                if(Config.loadBalance.equals("random")){
                    url = RandomBalance.RandomURL(list);
                }
                if (Config.loadBalance.equals("robin")){
                    url = CircleBalance.CircleURL(list);
                }
                if(Config.loadBalance.equals("")){
                    url = RandomBalance.RandomURL(list);
                }

                //TODO send返回结果为object更好
                String result = httpClient.send(url, invocation);

                return result;
            }
        });

        return (T) instance;
    }

}
