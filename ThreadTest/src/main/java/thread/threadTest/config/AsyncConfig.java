package thread.threadTest.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport{

    @Override
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2); // 기본적으로 실행 대기하는 thread의 갯수 설정
        executor.setMaxPoolSize(10); // 동시동작하는 최대 Thread pool 크기
        executor.setQueueCapacity(500); // thread pool que 크기
        executor.setThreadNamePrefix("mail-async-"); // spring이 생성하는 thread의 접두사 설정
        executor.initialize();

        return executor;
    }

    @Bean(name = "hndlrThreadExecutor")
    public Executor asyncThreadHndlr(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(5);
        threadPoolTaskExecutor.setThreadNamePrefix("hndlr-thread");
        return threadPoolTaskExecutor;
    }

}
