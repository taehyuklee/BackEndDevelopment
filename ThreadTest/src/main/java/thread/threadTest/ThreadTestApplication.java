package thread.threadTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThreadTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreadTestApplication.class, args);

		// Runtime runtime = new Runtime();

		// Process p = runtime.exec("routerHandler.process");

		// p.onExit();
	}

}
