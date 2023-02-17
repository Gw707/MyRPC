package http.provider;

import com.MyRPC.framework.protocol.http.HttpServer;
import com.MyRPC.framework.protocol.register.LocalRegister;
import com.MyRPC.framework.protocol.register.RemoteRegister;
import com.MyRPC.framework.protocol.register.URL;
import http.provider.HelloService.HelloService;
import http.provider.HelloServiceImpl.HelloServiceImpl;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 11:50
 * @Version 1.0
 */
public class provider {
    public static void main(String[] args) {
        //本地注册
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);

        //远程注册，将本地的host、port存到远程的注册中心中
        //TODO 获取本地的host、port, 此处固定为localhost：8080
        URL url = new URL("localhost", 8080);

        RemoteRegister.regist(HelloService.class.getName(), url);

        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);

    }
}
