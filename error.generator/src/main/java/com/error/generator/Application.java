package com.error.generator;

import com.error.generator.service.DeadlockService;
import com.error.generator.service.StackOverflowService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private final StackOverflowService stackOverflowService;
	private final DeadlockService deadlockService;

	public Application(StackOverflowService stackOverflowService, DeadlockService deadlockService) {
		this.stackOverflowService = stackOverflowService;
		this.deadlockService = deadlockService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			// Uncomment one at a time for testing
			deadlockService.causeDeadlock();              // Deadlock
			stackOverflowService.causeStackOverflow();  // StackOverflowError
		};
	}

}
