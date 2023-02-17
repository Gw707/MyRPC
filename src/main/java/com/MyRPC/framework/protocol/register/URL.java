package com.MyRPC.framework.protocol.register;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2022/12/7 16:51
 * @Version 1.0
 */
public class URL implements Serializable {
    private String hostname;
    private Integer port;

    public URL(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
