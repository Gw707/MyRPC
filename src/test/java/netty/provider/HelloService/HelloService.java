package netty.provider.HelloService;

import com.MyRPC.framework.protocol.netty.tcp.annotation.Service;
import netty.provider.HelloServiceImpl.HelloServiceImpl;

import java.util.List;

public interface HelloService {
    public String sayHello(String name) ;
    User getUsers();
}
