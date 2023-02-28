package netty.provider.HelloServiceImpl;


import netty.provider.HelloService.HelloService;
import netty.provider.HelloService.User;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 11:53
 * @Version 1.0
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello(String info) {
        return "server received ...." + info;
    }

    @Override
    public User getUsers() {
        return new User("yyy", "111");
    }
}
