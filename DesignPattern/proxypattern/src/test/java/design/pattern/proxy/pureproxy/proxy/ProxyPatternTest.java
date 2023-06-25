package design.pattern.proxy.pureproxy.proxy;

import org.junit.jupiter.api.Test;

import design.pattern.proxy.pureproxy.proxy.code.CacheProxy;
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


    //Cache
    @Test
    void cacheProxyTest(){
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute(); //처음에만 1초 걸린다
        client.execute(); //두번째부터는 바로 캐싱된 내용이 나온다.
        client.execute();
    }
    
}
