### 내부 호출은 Proxy객체를 거치지 않는다.

자바 언어에서 메서드 앞에 별도의 참조가 없으면 this 라는 뜻으로 자기 자신의 인스턴스를 가리킨다.
결과적으로 자기 자신의 내부 메서드를 호출하는 this.internal() 이 되는데, 여기서 this 는 실제 대상
객체(target)의 인스턴스를 뜻한다. 결과적으로 이러한 내부 호출은 프록시를 거치지 않는다. 따라서
어드바이스도 적용할 수 없다. <br>


### AspectJ를 직접사용하면 위와같은 일은 안일어난다.

그 이유를 말하자면, 동적이아니라 직접 Bytecode로 //AOP코드가 들어가기때문이다.

```java
//AspectJ를 사용할경우는 아래와 같이 바이트코드가 그냥 들어간다.
@Component
public class CallServiceV0 {

    public void external(){
        //AOP 코드 (바이트코드로)
        log.info("call external");
        internal(); //내부 메서드 호출 (this.internal())
    }

    public void internal(){
        //AOP 코드 (바이트코드로)
        log.info("call ineternal");
    }
    
}


```
------

### 위의 문제에 대한 대안이 몇 가지 존재한다

- 자기 자신에 대한 주입 
