package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import javax.swing.text.html.Option;
import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        /*@SpringBootTest로 하지 않는 이상 스프링컨테이너를 따로 띄우질 않는다. 이에 따라
        * Spring Contatiner인 AnnotationConfigApp에 직접 TestBean.class(설정정보)를 넣어서 Bean을 등록해주는 것이다.
         */

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{
        //Member는 @Component가 아니기때문에 스프링 컨테이너와 아무 상관이 없다
        //따라서 의존성 관계주입이 되질 않는다
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1=" + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2=" + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3=" + noBean3);
        }
    }

}
