package netty.provider.HelloService;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/28 18:53
 * @Version 1.0
 */

public class User implements Serializable {
    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    String name;
    String pwd;
}
