package design.pattern.proxy.config.v4_postprocessor;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import design.pattern.proxy.config.AppV1Config;
import design.pattern.proxy.config.AppV2Config;
import design.pattern.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import design.pattern.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import design.pattern.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class}) //여기서 AppV1, AppV2 Import해준 이유는 얘네는 ComponentScan대상이 아니기때문에 Configuration을 Import해야 한다.
public class BeanPostProcessorConfig {

    //Proxy로 등록하는 이 모든게 사라졌다. Spring이 bean을 등록할때 빈후처리기로 인터셉트해서 basePackage를 기준으로 proxy를 등록해준다.
    
    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace){
        return new PackageLogTracePostProcessor("design.pattern.proxy.app", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace){
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace); //advice LogTraceAdvice 앞서 만든것
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
