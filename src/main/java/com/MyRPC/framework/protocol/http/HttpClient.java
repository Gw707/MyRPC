package com.MyRPC.framework.protocol.http;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @Description 请求的处理由Server完成、请求的发送由Client发送
 * HttpClient实现类不止一种，需要在此处进行统一
 * @Author ygw
 * @Date 2022/12/7 13:08
 * @Version 1.0
 */
public class HttpClient {

    public String send(com.MyRPC.framework.register.URL tarURL, Invocation invocation){

        String result = null;

        try {
            //准备http的前置条件，url和tcp连接
            URL url = new URL("http", tarURL.getHostname(), tarURL.getPort(), "/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //以post方式发送请求
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            //将Invocation对象传给provider
            objectOutputStream.writeObject(invocation);

            objectOutputStream.flush();
            objectOutputStream.close();

            //等待provider返回处理的结果
            InputStream inputStream = httpURLConnection.getInputStream();
            result = IOUtils.toString(inputStream, Charset.defaultCharset());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(result);
        return result;
    }
}
