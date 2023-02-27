package com.MyRPC.framework.protocol.netty.tcp.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Description
 * 为防止黏包半包问题增加使用LTC解码器（LengthFieldBasedFrameDecoder）
 * 在此处根据自定义的协议对其构造进行封装， 具体参数说明如下：
 *  maxFrameLength      = 1024
 *  lengthFieldOffset   = 12  协议头的长度
 *  lengthFieldLength   = 4   描述内容长度的字节数，根据这个读取消息内容
 *  lengthAdjustment    = 0
 *  initialBytesToStrip = 0   但是对读的消息不跳过，解码后仍包含协议头的部分
 *
 * @Author ygw
 * @Date 2023/2/26 16:26
 * @Version 1.0
 */
public class MyFrameDecoder extends LengthFieldBasedFrameDecoder {
    public MyFrameDecoder() {
        super(1024, 12, 4, 0, 0);
    }
}
