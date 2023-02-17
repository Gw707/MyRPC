package com.MyRPC.framework.protocol.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 本地注册，将本地的接口、实现类进行注册
 * @Author ygw
 * @Date 2022/12/7 12:55
 * @Version 1.0
 */
public class LocalRegister {

    private static Map<String, Class> map = new HashMap<>();

    /**
     * 如果有多个实现类可以增加一个版本号参数进行区分
     * 将interfaceName与version拼接成key
     * @param interfaceName
     * @param implClass
     */
    public static void regist(String interfaceName, Class implClass){
        map.put(interfaceName, implClass);
    }

    public static Class getClass(String interfaceName){
        return map.get(interfaceName);
    }

}
