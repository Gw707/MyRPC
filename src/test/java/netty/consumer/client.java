package netty.consumer;

import netty.provider.HelloService.HelloService;
import com.MyRPC.framework.protocol.netty.tcp.NettyClient;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 15:39
 * @Version 1.0
 */
public class client {
    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        HelloService helloService = nettyClient.getInstance(HelloService.class);

        String result = helloService.sayHello("ygw");
        System.out.println(result);
    }
}
