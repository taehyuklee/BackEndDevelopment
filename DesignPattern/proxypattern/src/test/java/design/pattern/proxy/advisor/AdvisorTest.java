package design.pattern.proxy.advisor;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import design.pattern.proxy.common.advice.TimeAdvice;
import design.pattern.proxy.common.service.ServiceImpl;
import design.pattern.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AdvisorTest {

    @Test
    void adviosrTest1(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor= new  DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()); //Advisor는 하나의 PointerCut과 Advice를 가지고 있다.

        /*
         * proxyFactory.addAdvice(new TimeAdvice());  이건? 뭔고.
         * -> 이것도 결국은   DefaultPointcutAdvisor advisor= new  DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()); 이렇게 되어 있다. 안에 까보면
         */

        proxyFactory.addAdvisor(advisor); //pointcut이 True인 Advisor가 들어간다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void adviosrTest2(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor= new  DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice()); //Advisor는 하나의 PointerCut과 Advice를 가지고 있다.

        /*
         * proxyFactory.addAdvice(new TimeAdvice());  이건? 뭔고.
         * -> 이것도 결국은   DefaultPointcutAdvisor advisor= new  DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()); 이렇게 되어 있다. 안에 까보면
         */

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }


    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void adviosrTest3(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        NameMatchMethodPointcut pointCut = new NameMatchMethodPointcut(); //스프링이 제공하는 가장 간단한 Pointcut중 하나
        pointCut.setMappedName("save");
        DefaultPointcutAdvisor advisor= new  DefaultPointcutAdvisor(pointCut, new TimeAdvice()); //Advisor는 하나의 PointerCut과 Advice를 가지고 있다.

        /*
         * 여러 Pointcut이 존재하고 실무에서는 AspectJExpressionPointcut : aspectJ 표현식으로 매칭한다. 이 포인터컷을 많이 사용한다.
         */

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }





    /*------------------------------ Pointercut 직접 구현 -------------------------------- */
    static class MyPointCut implements Pointcut{

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
           return new MyMethodMatcher();
        }

    }


    static class MyMethodMatcher implements MethodMatcher{

        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);
            log.info("포인트컷 호출 method={} targetClass={}", method.getName(), targetClass);
            log.info("포인트컷 결과 result={}", result);
            return result;
        }

        @Override
        public boolean isRuntime() {
           return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            //...가변인자임
            throw new UnsupportedOperationException();
        }

    }
    
}