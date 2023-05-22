package spring.logtracing.version4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.logtracing.version4.trace.logtrace.LogTrace;
import spring.logtracing.version4.trace.logtrace.ThreadLocalLogTrace;


@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace(){
        //singleton으로 등록함.
        return new ThreadLocalLogTrace();
    }
    
}
