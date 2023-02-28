package com.MyRPC.framework.protocol.netty.tcp.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * 将实现类本地注册
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    Map<String, Object> map = new HashMap<>();
    String name() default "default service name";
    Class implClass();
}
