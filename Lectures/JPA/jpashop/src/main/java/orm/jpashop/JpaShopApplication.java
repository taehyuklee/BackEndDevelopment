package orm.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaShopApplication {
	// JPA Entity에는 기본 생성자가 필수임 (동적으로 Byte Code조작하거나 그럴때 사용됨 그래서 그럼)
	public static void main(String[] args) {
		SpringApplication.run(JpaShopApplication.class, args);
	}

}
