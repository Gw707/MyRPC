package netty.consumer;

import com.MyRPC.framework.protocol.netty.tcp.proxy.ProxyFactoryForNetty;
import netty.provider.HelloService.HelloService;
import netty.provider.HelloService.User;

/**
 * @Description TODO
 * 使用注解注册服务，调用时不再使用getInstance
 * @Author ygw
 * @Date 2023/2/14 15:39
 * @Version 1.0
 */
public class client {
    public static void main(String[] args) throws Exception {
        HelloService helloService = ProxyFactoryForNetty.getInstance(HelloService.class);

        String result = helloService.sayHello("ygw");
        User users = helloService.getUsers();
        System.out.println(users);
        System.out.println(result);
    }
}
