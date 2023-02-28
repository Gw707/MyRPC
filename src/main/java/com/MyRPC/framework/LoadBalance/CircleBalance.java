package com.MyRPC.framework.LoadBalance;

import com.MyRPC.framework.register.URL;

import java.util.List;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/28 18:33
 * @Version 1.0
 */
public class CircleBalance {
    private static int index = 0;

    public static URL CircleURL(List<URL> list){
        if(index >= list.size()){
            index = 0;
        }

        return list.get(index++);

    }
}
