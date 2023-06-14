package design.pattern.proxy.config.v5_autoproxy;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import design.pattern.proxy.config.AppV1Config;
import design.pattern.proxy.config.AppV2Config;
import design.pattern.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import design.pattern.proxy.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class}) 
public class AutoProxyConfig {
    
    // @Bean
    public Advisor advisor1(LogTrace logTrace){
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        /* 자동으로 pointcut을 기준으로 프록시를 생성하다보니 위의 메소드에 해당되는 모든 class, interface가 proxy로 만들어질수 있겠네;;
        * 자신이 모르는 어딘가에 걸릴수가 있다. -> 매우 정밀한 pointcut이 필요하다.
        * 그래서 실무에서는 AspectJExpressionPointcut만 사용한다.
        */

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace); //advice LogTraceAdvice 앞서 만든것
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

        
    //@Bean
    public Advisor advisor2(LogTrace logTrace){
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* design.pattern.proxy.app..*(..))");
        //패키지 기준으로 pointcut을 적용함.

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace); //advice LogTraceAdvice 앞서 만든것
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

            
    @Bean
    public Advisor advisor3(LogTrace logTrace){
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* design.pattern.proxy.app..*(..)) && !execution(* design.pattern.proxy.app..noLog(..))");
        //패키지 기준으로 pointcut을 적용함. 그래서 특정 메소드에는 적용하고싶지 않으면 위와같이 해야한다. (noLog 제외)

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace); //advice LogTraceAdvice 앞서 만든것
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
