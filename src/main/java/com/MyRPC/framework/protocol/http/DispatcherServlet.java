package com.MyRPC.framework.protocol.http;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 10:52
 * @Version 1.0
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //调用请求的处理逻辑
        new HttpServletHandler().handler(req, res);
    }
}
