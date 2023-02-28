package com.MyRPC.framework;

import com.MyRPC.framework.protocol.netty.tcp.annotation.Service;

public interface HelloService {
    public String sayHello(String name) ;
}
