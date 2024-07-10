package com.web.crawler;

import com.web.crawler.cralwer.service.CrawlingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@EnableMongoAuditing
@SpringBootApplication
public class CrawlerApplication {

	public static void main(String[] args) {

		SpringApplication.run(CrawlerApplication.class, args);
	}

}
