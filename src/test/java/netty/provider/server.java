package netty.provider;

import netty.provider.HelloService.HelloService;
import netty.provider.HelloServiceImpl.HelloServiceImpl;
import com.MyRPC.framework.protocol.netty.tcp.bootstrap.NettyServer;
import com.MyRPC.framework.register.LocalRegister;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/14 15:25
 * @Version 1.0
 */
public class server {
    public static void main(String[] args) throws InterruptedException {
        //先到注册中心进行注册
        //TODO 远程注册 map： 接口名 - ip：port
        //TODO 本地注册 map：接口名 - 实现类
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);
        new NettyServer().startServer0();
    }
}
