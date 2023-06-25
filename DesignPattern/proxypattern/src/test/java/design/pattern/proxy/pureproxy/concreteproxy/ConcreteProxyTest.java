package design.pattern.proxy.pureproxy.concreteproxy;

import org.junit.jupiter.api.Test;

import design.pattern.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import design.pattern.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import design.pattern.proxy.pureproxy.concreteproxy.code.TimeProxy;

public class ConcreteProxyTest {

    //Proxy적용하지 않았을때,
    @Test
    void noProxy(){
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }
    /* 여기서 의문 Client는 구체클래스를 받아왔는데 프록시를 어떻게 적용할수 있는 것일까? 
     * (원래는 Proxy객체에서 implements를 통해 받아온 target을 꾸미는 역할을 했었다)*/


    /*결과론적으로 상속받아서 사용한다 */
    @Test
    void addProxy(){
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient client = new ConcreteClient(timeProxy); 
        /*Client에서는 구현체인 concreteLogic을 받아와야하지만, TimeProxy에서 ConcreteLogic을 상속받음으로써(다형성을 이용하여) TimeProxy의 부모가 ConcreteLogic형태를 띄게 됨.
         * 위 사실을 이용해서 client에 timeProxy를 넣을수가 있게 된다. (인터페이스에서는 구현, 구현체에서는 상속을 이용하여 다형성을 이용)*/ 
        client.execute();        
    }
}
