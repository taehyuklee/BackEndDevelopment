package spring.logtracing.version3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.logtracing.version3.trace.logtrace.FieldLogTrace;
import spring.logtracing.version3.trace.logtrace.LogTrace;

@Configuration("logTraceConfig3")
public class LogTraceConfig {

    @Bean(name="logTrace3")
    public LogTrace logTrace(){
        //singleton으로 등록함.
        return new FieldLogTrace();
    }
    
}
