package design.pattern.templatemethod.pattern;

import org.junit.jupiter.api.Test;

import design.pattern.templatemethod.pattern.code.AbstractTemplate;
import design.pattern.templatemethod.pattern.code.SubClassLogic1;
import design.pattern.templatemethod.pattern.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateMethodTest {

    @Test
    void TemplateMethodV0(){
        logic1();
        logic2();
    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        //핵심 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //핵심 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2(){
        long startTime = System.currentTimeMillis();
        //핵심 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //핵심 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }


    
    /*
     * 템플릿 메소드 패턴 적용함
     */
    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }




    /*
     * 위와 같이 SubClassLogic1처럼 따로 구현체를 만들지 않고 익명 Class를 활용해서 구현해보겠다.
     * 익명클래스는 구현체를 따로 만들지 않고 - 객체 인스턴스르 ㄹ새엇ㅇ하는 동시에 생성할 클래스를 상속 받은 자식 클래스를 정의할수 있다. (SubClassLogic1처럼 할 필요 없이)
     */
    @Test
    void templateMethodV2(){
        AbstractTemplate template1 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
            
        };

        //익명 클래스의 이름을 한 번 찍어보자
        log.info("클래스 이름1={}", template1.getClass());
        //결과 TemplateMethodTest$1 이름이 없어서 임의로 $1로 이름을 만들어준 것

        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비즈니스 로직2 실행");
            }
            
        };

        //익명 클래스의 이름을 한 번 찍어보자
        log.info("클래스 이름2={}", template2.getClass());
        template2.execute();
    }
    
}
