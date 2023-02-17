package com.MyRPC.framework.protocol.http;

import com.MyRPC.framework.Invocation;
import com.MyRPC.framework.protocol.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * @Description 通过http接收到Servlet请求，在此处增加请求的处理逻辑，与DispatcherServlet进行解耦
 * @Author ygw
 * @Date 2022/12/7 10:55
 * @Version 1.0
 */
public class HttpServletHandler {

    public void handler(HttpServletRequest req, HttpServletResponse res){
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();

            String interfaceName = invocation.getInterfaceName();
            String methodName = invocation.getMethodName();
            Class[] paramTypes = invocation.getParamTypes();
            Object[] params = invocation.getParams();

            //根据接口名获取对应的实现类
            Class clazz = LocalRegister.getClass(interfaceName);

            //根据方法名和参数列表获取要执行的方法
            Method method = clazz.getMethod(methodName, paramTypes);

            //利用反射执行调用的方法
            Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), params);

            //将方法调用的结果返回
            IOUtils.write(result.toString(), res.getOutputStream(), Charset.defaultCharset());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
