package http.consumer;

import com.MyRPC.framework.protocol.http.ProxyFactoryForHttp;
import http.provider.HelloService.HelloService;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 11:50
 * @Version 1.0
 */
public class consumer {
    public static void main(String[] args) {



        //TODO 使用动态代理，简化调用过程
        HelloService helloService = ProxyFactoryForHttp.getInstance(HelloService.class);

        String result = helloService.sayHello("ygw");
        System.out.println(result);

//        Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello",
//                new Class[]{String.class}, new Object[]{"xxx"});
//
//        HttpClient httpClient = new HttpClient();
//        httpClient.send("localhost", 8080, invocation);
    }
}
