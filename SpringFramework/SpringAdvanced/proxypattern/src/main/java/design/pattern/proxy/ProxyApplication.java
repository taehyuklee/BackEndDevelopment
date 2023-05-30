package design.pattern.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import design.pattern.proxy.config.AppV1Config;

/*아래 basePackage범위에서 Config가 빠져있어서 (ComponentScan범위 밖) - 여기서 따로 Config를 Import해주는거임 (앞으로 버전에 따라 바꿔갈거임) 
버전에따라 달라져야하는데 Componentscan하면 앞으로 올 V1, V2, V3모두 한꺼번에 등록되니까 이렇게 따로 뺀거임.*/
@Import(AppV1Config.class) 
@SpringBootApplication(scanBasePackages = "design.pattern.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
