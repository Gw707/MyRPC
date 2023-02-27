package com.MyRPC.framework.register;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 不在同一台主机上时服务的注册
 * 此时需要增加中间件来存储注册信息ZooKeeper、redis等
 * @Author ygw
 * @Date 2022/12/7 16:48
 * @Version 1.0
 */
public class RemoteRegister {

    //根据接口名查询可以使用该服务的URL
    private static Map<String, List<URL>> map = new HashMap<>();

    public static void regist(String interfaceName, URL url){
        readFile();
        List<URL> list = map.get(interfaceName);
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(url);
        map.put(interfaceName, list);

        saveFile();
    }

    public static List<URL> get(String interfaceName){
        readFile();
        return map.get(interfaceName);
    }


    public static void saveFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/Code/java/MyRPC/register.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
            objectOutputStream.writeObject(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readFile(){
        try {
            FileInputStream fileInputStream = new FileInputStream("/Code/java/MyRPC/register.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            map = (Map) objectInputStream.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
