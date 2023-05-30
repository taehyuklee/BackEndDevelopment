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