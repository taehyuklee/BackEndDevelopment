## 프록세 패턴관련해서 배우고 적용해보는 패키지

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

### 적용 사례
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