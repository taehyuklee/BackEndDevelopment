package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){

        //ApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleCnfig.class);
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleCnfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

        //close method는 ApplicationContext에는 존재하지 않는다. 이에 따라 ConfigurableApplicationContext가 필요함.
        //ConfigurableApplicationContext는 AnnotationConfigApplicationContext을 상속받는다.

        //https://codedragon.tistory.com/6155
        //상속관계를 보기 위해소 ctrl + h (인텔리 제이) https://appleg1226.tistory.com/27
    }

    @Configuration
    static class LifeCycleCnfig{
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dv");
            return networkClient;
        }

        @Bean
        public TestInterface testInterface(){
            return new TestInterfaceImpl();
        }
    }
}
