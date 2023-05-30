package design.pattern.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import design.pattern.proxy.config.AppV1Config;
import design.pattern.proxy.config.AppV2Config;

/*아래 basePackage범위에서 Config가 빠져있어서 (ComponentScan범위 밖) - 여기서 따로 Config를 Import해주는거임 수동으로 빈 등록하는 것을 목표로 하는거*/
@Import({AppV1Config.class, AppV2Config.class}) 
@SpringBootApplication(scanBasePackages = "design.pattern.proxy.app.version3")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
