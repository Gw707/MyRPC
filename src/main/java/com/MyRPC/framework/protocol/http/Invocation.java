package com.MyRPC.framework.protocol.http;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description 此处定义的是远程调用的model
 * 必须要：
 *      1、接口名
 *      2、方法名
 *      3、参数类型列表
 *      4、参数值列表
 *      5、版本号（可选，用于区分接口的多个实现类）
 * 才能完成一次完整的方法调用
 * @Author ygw
 * @Date 2022/12/7 11:32
 * @Version 1.0
 */
public class Invocation implements Serializable {

    //接口名、方法名、参数类型列表、参数值列表、版本号

    private String interfaceName;

    private String methodName;

    private Class[] paramTypes;

    private Object[] params;

    //如果一个接口有多个实现类，用版本号进行区分
    private String version;

    public Invocation(String interfaceName, String methodName, Class[] paramTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", params=" + Arrays.toString(params) +
                ", version='" + version + '\'' +
                '}';
    }
}
