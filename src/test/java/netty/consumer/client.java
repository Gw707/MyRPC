package netty.consumer;

import com.MyRPC.framework.protocol.netty.tcp.Bootstrap.ProxyFactoryForNetty;
import netty.provider.HelloService.HelloService;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 15:39
 * @Version 1.0
 */
public class client {
    public static void main(String[] args) throws Exception {
        HelloService helloService = ProxyFactoryForNetty.getInstance(HelloService.class);

        String result = helloService.sayHello("ygw");
        System.out.println(result);
    }
}
