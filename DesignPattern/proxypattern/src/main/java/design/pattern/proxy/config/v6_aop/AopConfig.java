package design.pattern.proxy.config.v6_aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import design.pattern.proxy.config.AppV1Config;
import design.pattern.proxy.config.AppV2Config;
import design.pattern.proxy.config.v6_aop.aspect.LogTraceAspect;
import design.pattern.proxy.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class}) 
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }
    
    
}
