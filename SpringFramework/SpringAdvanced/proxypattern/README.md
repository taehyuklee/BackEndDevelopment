## 프록세 패턴관련해서 배우고 적용해보는 패키지

* main에는 실제 MVC pattern에 어떻게 적용했는지를 보게 될 것이고, test패키지에서는 각 패턴에 대한 예제를 다루게 된다.

예제는 크게 3가지 상황으로 만든다.
v1 - 인터페이스와 구현 클래스 - 스프링 빈으로 수동 등록
v2 - 인터페이스 없는 구체 클래스 - 스프링 빈으로 수동 등록
v3 - 컴포넌트 스캔으로 스프링 빈 자동 등록

V1, V2, V3를 보면 모두 @Service, @Repository와 같은 어노테이션을 달지 않고 Configuration에서 의존성 주입을 해주었다. (여기서 싱글톤 Bean으로 등록해줌)

<br><br>

```java

@Import({AppV1Config.class, AppV2Config.class}) 
@SpringBootApplication(scanBasePackages = "design.pattern.proxy.app.version3")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}

```
Spring boot 3.0이상에서 V1, V2는 모두 수동 주입을 할 것임. 그래서 {AppV1Config.class, AppV2Config.class} 여기서 따로 Configuration해주는 부분이 있지만, 2.x버전에서는 Spring boot에서 @RequestMapping만 있어도 Controller로 인식이 가능했으나, 3.xx버전부터는 @Controller가 없으면 인식이 불가해진다. 이에따라 @RestController를 달면 Component Scan대상이되어 자동으로 빈 주입이 되고, 결국 AppConfig에서 충돌이 일어나게된다. 따라서 앞으로 스프링 빈 자동 등록할 version3으로 베이스 패키지를 맞추어 위와같이 설정하고 시작한다.

<br>

### 기존의 문제점  <b>(김영한님의 강의자료에서)</b>
하지만 이 요구사항을 만족하기 위해서 기존 코드를 많이 수정해야 한다. 코드 수정을 최소화 하기 위해 템플릿 메서드 패턴과 콜백 패턴도 사용했지만, 결과적으로 로그를 남기고 싶은 클래스가 수백개라면 수백개의 클래스를 모두 고쳐야한다. 로그를 남길 때 기존 원본 코드를 변경해야 한다는 사실 그 자체가 개발자에게는 가장 큰 문제로 남는다.
<br>
### 요구사항 추가
<b>원본 코드를 전혀 수정하지 않고, 로그 추적기를 적용해라.</b>

* 특정 메서드는 로그를 출력하지 않는 기능
	* 보안상 일부는 로그를 출력하면 안된다.

<br>

* 다음과 같은 다양한 케이스에 적용할 수 있어야 한다.
v1 - 인터페이스가 있는 구현 클래스에 적용
v2 - 인터페이스가 없는 구체 클래스에 적용
v3 - 컴포넌트 스캔 대상에 기능 적용
가장 어려운 문제는 원본 코드를 전혀 수정하지 않고, 로그 추적기를 도입하는 것이다. 이 문제를 해결하려면 프록시(Proxy)의 개념을 먼저 이해해야 한다.

<br><br>

### 프록시의 주요 기능

프록시를 통해서 할 수 있는 일은 크게 2가지로 구분할 수 있다.

1. 접근 제어
권한에 따른 접근 차단
캐싱
지연 로딩

2. 부가 기능 추가
원래 서버가 제공하는 기능에 더해서 부가 기능을 수행한다.
(예) 요청 값이나, 응답 값을 중간에 변형한다.
(예) 실행 시간을 측정해서 추가 로그를 남긴다.

결론: 프록시 객체가 중간에 있으면 크게 접근 제어와 부가 기능 추가를 수행할 수 있다.

<br><br>

### GOF 디자인 패턴
둘다 프록시를 사용하는 방법이지만 GOF 디자인 패턴에서는 이 둘을 의도(intent)에 따라서 프록시 패턴과 데코레이터 패턴으로 구분한다.
* 프록시 패턴: 접근 제어가 목적
* 데코레이터 패턴: 새로운 기능 추가가 목적

둘다 프록시를 사용하지만, 의도가 다르다는 점이 핵심이다. 용어가 프록시 패턴이라고 해서 이 패턴만 프록시를 사용하는 것은 아니다. 데코레이터 패턴도 프록시를 사용한다.

<br><br>

### CacheProxy에서는 접근제어자 역할을 해보는 CacheProxy에 대해서 만들어 볼 것이다.
* ProxyPattern을 통해서 중간에 클라이언트 코드나 target subject의 코드 변화없이 중간에 접근제어를 했다는 것에 의의를 둔다. 

<br>

### 결론 
* 프록시를 사용하고 해당 프록시가 접근 제어가 목적이라면 프록시 패턴이고, 새로운 기능을 추가하는 것이 목적이라면 데코레이터 패턴이 된다.

### 적용 사례 1 (Interface기반의 Proxy를 받아와서 사용하는 Case)
* v1_proxy.interface_proxy안에 각 oder, service, repository관련한 proxy객체들이 있고, 그리고 v1_proxy에 InterfaceProxyConfig.java는 AppV1Config.java와 마찬가지로 밖에서 Bean을 등록해주면서 조립해주는 역할을 한다. 이때 로그를 찍는것을 proxy 객체들이 하기때문에 빈 등록을 모두 Proxy객체로 해주도록 한다. 
<br><br>

```java
@Configuration
public class InterfaceProxyConfig {

    //사실상 모두 Proxy를 넣어줘야 log를 찍고 반환해준다.

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) { //Proxy를 반환해야한다
        OrderControllerV1Impl target = new OrderControllerV1Impl(orderService(logTrace)); //proxy에 넣어줄 실제 target 구현체
        return new OrderControllerInterfaceProxy(target, logTrace); //여기서 Impl에서도 Proxy를 호출해줘야 한다.
    }


    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl target = new OrderServiceV1Impl(orderRepository(logTrace)); //proxy에 넣어줄 실제 target 구현체
        return new OrderServiceInterfaceProxy(target, logTrace); //proxy를 넣어준다
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl target = new OrderRepositoryV1Impl(); //proxy에 넣어줄 실제 target 구현체
        return new OrderRepositoryInterfaceProxy(target, logTrace); //proxy를 넣어준다
    }

}

```

<br><br>

### 적용 사례 2 (구현체기반으로 Proxy를 받아와서 사용하는 Case)
<br>

* 클래스 기반 프록시 도입 지금까지 인터페이스를 기반으로 프록시를 도입했다. 그런데 자바의 다형성은 인터페이스를 구현하든, 아니면 클래스를 상속하든 상위 타입만 맞으면 다형성이 적용된다. 쉽게 이야기해서 인터페이스가 없어도 프록시를 만들수 있다는 뜻이다. 그래서 이번에는 인터페이스가 아니라 클래스를 기반으로 상속을 받아서 프록시를 만들어보겠다. <b>(출처: 김영한님 수업자료)</b>

<br>

```java
public class TimeProxy extends ConcreteLogic{
    
    private ConcreteLogic concreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic){
        this.concreteLogic = concreteLogic;
    }

    @Override
    public String operation(){
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String realData = concreteLogic.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime={}ms", resultTime);

        return realData;
        
    }

}
```

<br><br>

```java
/*결과론적으로 상속받아서 사용한다 */
@Test
void addProxy(){
    ConcreteLogic concreteLogic = new ConcreteLogic();
    TimeProxy timeProxy = new TimeProxy(concreteLogic);
    ConcreteClient client = new ConcreteClient(timeProxy); 
    client.execute();        
}
```


* Client에서는 구현체인 concreteLogic을 받아와야하지만, TimeProxy에서 ConcreteLogic을 상속받음으로써(다형성을 이용하여) TimeProxy의 부모가 ConcreteLogic형태를 띄게 됨. 위 사실을 이용해서 client에 timeProxy를 넣을수가 있게 된다. (인터페이스에서는 구현, 구현체에서는 상속을 이용하여 다형성을 이용) - <b>자바에서는 부모타입에 자식타입을 할당할수 있다. </b>

<br><br>

```java
public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private final OrderControllerV2 target; //상속 받았는데 굳이 이렇게 주입받는 이유는 뭐지?
    private final LogTrace logTrace;
    
    public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
      
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        
        try{
            status = logTrace.begin("OrderController.request()");

            String result = target.request(itemId); //실제 target의 로직

            logTrace.end(status);

            return result;

        } catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
    
}
```

<br>

* 중요 포인트! <br>
    * 위와 같이 상속받았으면, 상속받은 기능을 그대로 사용하면 되는데, 굳이 target을 따로 Injection받아서 Proxy기능을 추가하게 된다. 그 이유는 무엇일까?
    OOP에서 5원칙인 DIP에 의하면 밖에서 Dependency를 주입해줘야 한다. 즉, 내부적으로 서로의 Dependency를 걸면 안된다. 이에 따라 구현체임에도 불구하고 다형성을 이용하기 위해 상속을 받은거고, 만약 상속받은 기능을 사용한다면, 내부적으로 super(매개변수)를 통해 상속받은 자식클래스만의 구현체가 생겨버리게 된다. 즉, dependency가 걸리게 된다.
<br><br>

* 따라서, 내부적으로 super(매개변수)를 통해 상속받은 기능을 사용하는 것이 아니라, 다형성만을 위해 상속받고, 밖에서 target은 injection받아 사용한다. (Interface Proxy패턴과 일치함)
    * 항상 프록시는 프록시가 호출할 target대상이 있어야 한다.

<br><br>
김영한님 강의 자료에서

* 인터페이스 기반 프록시 vs 클래스 기반 프록시
1. 인터페이스가 없어도 클래스 기반으로 프록시를 생성할 수 있다.
클래스 기반 프록시는 해당 클래스에만 적용할 수 있다. 인터페이스 기반 프록시는 인터페이스만 같으면 모든 곳에 적용할 수 있다.

* 클래스 기반 프록시는 상속을 사용하기 때문에 몇가지 제약이 있다.
1. 부모 클래스의 생성자를 호출해야 한다.(앞서 본 예제)
2. 클래스에 final 키워드가 붙으면 상속이 불가능하다.
3. 메서드에 final 키워드가 붙으면 해당 메서드를 오버라이딩 할 수 없다.

* 이렇게 보면 인터페이스 기반의 프록시가 더 좋아보인다. 맞다. 인터페이스 기반의 프록시는 상속이라는 제약에서 자유롭다. 프로그래밍 관점에서도 인터페이스를 사용하는 것이 역할과 구현을 명확하게 나누기때문에 더 좋다. 인터페이스 기반 프록시의 단점은 인터페이스가 필요하다는 그 자체이다. 인터페이스가 없으면 인터페이스 기반 프록시를 만들 수 없다.

### 위의 자바 패턴으로의 프록시 패턴의 한계점 
* 너무 많은 프록시 클래스 지금까지 프록시를 사용해서 기존 코드를 변경하지 않고, 로그 추적기라는 부가 기능을 적용할 수 있었다.
그런데 <b>문제는 프록시 클래스를 너무 많이 만들어야 한다는 점이다.</b> 잘 보면 프록시 클래스가 하는 일은 LogTrace 를 사용하는 것인데, 그 로직이 모두 똑같다. 대상 클래스만 다를 뿐이다. 만약 적용해야 하는 대상 클래스가 100개라면 프록시 클래스도 100개를 만들어야한다. 프록시 클래스를 하나만 만들어서 모든 곳에 적용하는 방법은 없을까? 바로 다음에 설명할 동적 프록시 기술이 이 문제를 해결해준다.

* 여태까지 예제에 의하면, 코드를 안건드리고 부가기능을 추가하는건 좋았는데, 만약 클래스가 100개라면 똑같은 Proxy객체를 100개 만들어야 한다는 한계점이 있다
    * -> 이후에 동적 프록시 기술을 이용해 같은 기능의 Proxy를 원하는 클래스 모두에 적용해보고자 한다.


## DynamicProxy
* 동적프록세에 관련된 패키지는 test -> jdkdynmaic에 있다.

```java
    @Test
    void reflection0(){

        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        //공통 로직1 종료


        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
        //공통 로직2 종료

    }
```
* 위의 코드에서 log.info앞뒤는 template method 또는 전략패턴을 통해서 따로 뺄수 있다고해보자, 하지만 target.callA()의 경우 target의 Class가 인터페이스를 통해서 나뉘는 구현체도 아니고 하나의 Class내의 다른 method인데, 이걸 어떻게 공통 로직으로 뺄수 있을까? 이미 method마다 이름이 callA(), callB()로 박혀있는데 자바 리플렉션 기술을 이용하면 된다.

* class의 메타정보를 통해서 method를 추상화해서 공통화가 가능하다.

<br>
jdkdynamic 패키지 안에 보면 AImpl, BImpl과 관련해서 프록시를 각각 만들지 않고 공통 프록시를 하나만 동적으로 만들어 수행하는 내용을 담고 있다.

```java
@Slf4j //해당 InvoactionHandler는 Interface만 사용가능하다.
public class TimeInvocationHandler implements InvocationHandler{

    private final Object target; //항상 프록시는 Proxy가 호출할 대상이 있어야 한다

    public TimeInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        method.invoke(target, args); //method에 인수들 넘기는건 args로 한다 (어떤 method를 수행할지는 어떻게 알수 있어?)

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return null;
    }
    
}

```
<br>

```java
@Test
void dynamicA(){
    AInterface target = new AImpl();
    TimeInvocationHandler handler = new TimeInvocationHandler(target); //실제 호출 대상을 Proxy에 넣어주는 역할

    Object proxy = Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler); //동적으로 proxy객체가 생성된다.
    
    //AInterface를 기반으로 만들어진다 (InvocationHandler를 이용해서 proxy객체가 생성된다. (인터페이스))
    AInterface proxyA = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler); 
    /*Interface가 아니면 java.lang.IllegalArgumentException: design.pattern.proxy.jdkdynamic.code.AImpl is not an interface 해당 에러가 뜬다 */

    proxyA.call();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass()); //proxyClass=class jdk.proxy2.$Proxy8 동적 프록시
}
```
<br>
내가 그린 그림을 생각해보며 위의 코드를 따라가보자.

* 결과적으로 프록시 클래스를 수 없이 만들어야 하는 문제를 해결하는 동시에 부가 기능 로직도 하나의 클래스에 모아서 단일 책임 원칙(SRP)도 지킬수 있게 되었다.

<br>

```java
@Configuration
public class DynamicProxyBasicConfig {

    //new Class[]{OrderServiceV1.class} 자바에서 배열 생성하는거 int[]{1,2,3} 이런식으로 하지 참.

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace){
        OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, handler); //proxy를 반환해준다
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace){
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, handler); //proxy를 반환해준다
    }
    
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace){
        OrderRepositoryV1 target = new OrderRepositoryV1Impl();
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, handler); //proxy를 반환해준다
    } 
}

```
위의 Configuration과 같이 동적 프록시를 Bean에 주입해준다. 하지만 아직도 문제가 하나 남아있다. 바로 no-log method관련해서 Controller에 no-log 로그를 찍으면 안되는 method에도 동적 프록시가 일괄적으로 적용되는 문제가 있다. (모든 메소드에 다 적용된다) <br>
<br>

```java
@GetMapping("/v1/request")
String request(@RequestParam("itemId") String itemId); //interface에는 꼭 RequestParam이름을 명시적으로 적시한다 (Compile때 문제가 생길수 있음)

@GetMapping("/v1/no-log")
String noLog();
```
모든 method에 적용되는 것을 어떻게 해서 하나만 적용할지에 대해서도 알아봐야 한다.


<br><br>

### test Package내부에 cglib package는 cglib만으로 구현체 클래스 프록시를 만들었던 케이스임.
- 위 cglib은 순수 Code Generation Library로 구현한 케이스 이다. (Spring내부에서 OpenSource인 CGLIB을 가져왔다. import org.springframework임.)
- Interface, class 모두를 통틀어서 하나의 method로 추상화할수 없을까? 어떨때는 MethgoInterceptor를 어떨때는 InvocationHandler를 좀 자동으로 하나로 추상화될수 있는게 있으면 좋겠다. 이에 따라 Spring에서는 Advice로 추상화 해 놓았다. (이때도 MethodInterceptor가 사용되는데 이때는 org.aopalliance.intercept에서 꺼내와야 한다)


