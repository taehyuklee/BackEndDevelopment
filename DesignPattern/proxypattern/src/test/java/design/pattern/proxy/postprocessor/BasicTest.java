package design.pattern.proxy.postprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

public class BasicTest {
    
    @Test
    void basicConfig(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        //A는 빈으로 등록된다.
        // A a = applicationContext.getBean("beanA", A.class);
        // a.helloA();
        /* 빈 후처리기 이후 생기는 에러
         * org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'beanA' is expected to be of type 'design.pattern.proxy.postprocessor.BasicTest$A' but was actually of type 'design.pattern.proxy.postprocessor.BasicTest$B'
         */


        //빈 후처리기 등록 이후 (아래 public A a() -> 후처리기가 가로채서 beanA라는 이름으로 B객체를 저장해버린다.) - 후킹 포인트
        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        //B는 빈으로 등록되지 않았다.
        //B bean =applicationContext.getBean("beanB", B.class);
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()-> applicationContext.getBean(A.class));
    }

    @Configuration
    static class BeanPostProcessorConfig{

        @Bean(name = "beanA")
        public A a(){ //BasicConfig
            return new A();
        }

        @Bean
        public AtoBPostProcessor helloPostProcessor(){
            return new AtoBPostProcessor();
        }
    }

    @Slf4j
    static class A{
        public void helloA(){
            log.info("hello A");
        }
    }

    @Slf4j
    static class B{
        public void helloB(){
            log.info("hello B");
        }
    }

    @Slf4j //BeanPostProcessr를 구현함으로써 빈 후처리기를 생성할수 있다.
    static class AtoBPostProcessor implements BeanPostProcessor{

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}", beanName, bean);
            if(bean instanceof A){
                return new B();
            }
            return bean;
        }

    }
}
