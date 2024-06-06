package design.pattern.template.version5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import design.pattern.template.version5.trace.logtrace.LogTrace;
import design.pattern.template.version5.trace.logtrace.ThreadLocalLogTrace;


@Configuration("logTraceConfig5")
public class LogTraceConfig {

    @Bean(name="logTrace5")
    public LogTrace logTrace(){
        return new ThreadLocalLogTrace();
    }
    
}
