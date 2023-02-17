package http.provider.HelloServiceImpl;

import http.provider.HelloService.HelloService;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 11:53
 * @Version 1.0
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name + "!!!";
    }
}
