package com.MyRPC.framework.protocol.netty.tcp.annotation;

import com.MyRPC.framework.protocol.netty.tcp.proxy.ProxyFactoryForNetty;
import com.MyRPC.framework.register.LocalRegister;
import javassist.tools.reflect.Reflection;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * @Description TODO
 * 服务端调用Service注解，表明使用Service的接口，暴露服务
 * @Author ygw
 * @Date 2023/2/28 10:29
 * @Version 1.0
 */
@Slf4j
public class ServiceAnnotationProcessor {
    Object instance;
    public void parseService(){
        //TODO 从配置文件读取包名
        String packageName = "com.MyRPC.framework";
        Reflections reflection = new Reflections(packageName);
        Set<Class<?>> set = reflection.getTypesAnnotatedWith(Service.class);
        for (Class<?> clazz : set) {
            Service annotation = clazz.getAnnotation(Service.class);
            log.debug(annotation.toString());
            //TODO 或者是直接注册到Service的map中
            LocalRegister.regist(clazz.getName(), annotation.implClass());
        }
    }
    public void parseReference(){
        //TODO 从配置文件读取包名
        String packageName = "com.MyRPC.framework";
        Reflections reflection = new Reflections(packageName);
        Set<Class<?>> set = reflection.getTypesAnnotatedWith(Reference.class);
        for (Class<?> clazz : set) {
            Reference annotation = clazz.getAnnotation(Reference.class);
            log.debug(annotation.toString());
            try {
                instance = ProxyFactoryForNetty.getInstance(clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Reference.map.containsKey(clazz.getName())) {
                return;
            }
            Reference.map.put(clazz.getName(), instance);
        }
    }
}
