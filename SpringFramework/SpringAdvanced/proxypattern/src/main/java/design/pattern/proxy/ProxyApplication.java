package design.pattern.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import design.pattern.proxy.config.v2_dynamicproxy.handler.DynamicProxyFilterConfig;
import design.pattern.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import design.pattern.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import design.pattern.proxy.trace.logtrace.LogTrace;
import design.pattern.proxy.trace.logtrace.ThreadLocalLogTrace;

/*아래 basePackage범위에서 Config가 빠져있어서 (ComponentScan범위 밖) - 여기서 따로 Config를 Import해주는거임 수동으로 빈 등록하는 것을 목표로 하는거*/
// @Import({AppV1Config.class, AppV2Config.class}) 
// @Import(DynamicProxyFilterConfig.class)
// @Import(ProxyFactoryConfigV1.class)
@Import(ProxyFactoryConfigV2.class)
@SpringBootApplication(scanBasePackages = "design.pattern.proxy.app.version3")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean // LogTrace와 남기는 로직 서비스를 Bean으로 등록해준다.
	public LogTrace logTrace(){
		return new ThreadLocalLogTrace();
	}

}
