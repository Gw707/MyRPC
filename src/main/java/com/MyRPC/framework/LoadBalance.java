package com.MyRPC.framework;

import com.MyRPC.framework.protocol.register.URL;

import java.util.List;
import java.util.Random;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 17:08
 * @Version 1.0
 */
public class LoadBalance {

    public static URL RandomURL(List<URL> list){

        int index = new Random().nextInt(list.size() - 1);

        return list.get(index);

    }

    //TODO 增加其他的轮询策略


}
