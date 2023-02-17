package netty.provider.HelloServiceImpl;


import netty.provider.HelloService.HelloService;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 11:53
 * @Version 1.0
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String info) {
        return "server received ...." + info;
    }
}
