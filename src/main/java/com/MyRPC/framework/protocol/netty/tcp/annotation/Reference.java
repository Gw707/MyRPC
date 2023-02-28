package com.MyRPC.framework.protocol.netty.tcp.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;


/**
 * 将代理类本地注册
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {
    Map<String, Object> map = new HashMap<>();
}
