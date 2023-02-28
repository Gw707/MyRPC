package com.MyRPC.framework.register;

import com.alibaba.fastjson2.JSON;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/27 21:44
 * @Version 1.0
 */
public class RedisRegister {

    private static Jedis redisTemplate;

    public RedisRegister() {
        redisTemplate = new Jedis("127.0.0.1", 6379);
    }

    /**
     * 如果有多个实现类可以增加一个版本号参数进行区分
     * 将interfaceName与其他信息拼接成key
     * 获取一些本地信息拼接成redis的key更好，然后注册中心可以对远程的各种服务进行监控
     * 注册中心最好与RPC服务接口间也有心跳检测
     * @param interfaceName
     * @param implClass
     */
    public static void regist(String interfaceName, Class implClass){
        String clazz = JSON.toJSONString(implClass);
        redisTemplate.set(interfaceName, clazz);
    }

    public static Class getClass(String interfaceName){
        String str = redisTemplate.get(interfaceName);
        Class clazz = JSON.parseObject(str, Class.class);
        return clazz;
    }
}
