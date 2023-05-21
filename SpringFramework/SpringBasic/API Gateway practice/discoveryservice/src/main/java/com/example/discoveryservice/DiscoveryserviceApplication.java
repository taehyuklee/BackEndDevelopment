package com.example.discoveryservice;

import com.netflix.discovery.EurekaNamespace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication //Bootstrap같은 것 main()과 같은. @SpringBootApplication Annotation을 먼저 검색한다.
@EnableEurekaServer //Server의 역할을 주게 된다.
public class DiscoveryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryserviceApplication.class, args);
	}

}
