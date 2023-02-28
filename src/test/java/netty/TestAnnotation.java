package netty;

import com.MyRPC.framework.protocol.netty.tcp.annotation.ServiceAnnotationProcessor;

/**
 * @Description TODO
 * @Author ygw
 * @Date 2023/2/28 10:55
 * @Version 1.0
 */
public class TestAnnotation {
    public static void main(String[] args) {
        new ServiceAnnotationProcessor().parseService();
    }
}
