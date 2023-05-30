package design.pattern.proxy.pureproxy.proxy;

import org.junit.jupiter.api.Test;

import design.pattern.proxy.pureproxy.proxy.code.ProxyPatternClient;
import design.pattern.proxy.pureproxy.proxy.code.RealSubject;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }
    
}
