package com.MyRPC.framework.protocol.netty.tcp.bootstrap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/28 11:04
 * @Version 1.0
 */
public class Config {
    static FileInputStream in;
    public static String loadBalance;
    public static String host;
    public static int port;
    static {
        try {
            in = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(in);
            host = properties.getProperty("rpc.Host");
            port = Integer.parseInt(properties.getProperty("rpc.Port"));
            //properties.getProperty("rpc.ApiPackage");
            loadBalance = properties.getProperty("rpc.LoadBalance");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
