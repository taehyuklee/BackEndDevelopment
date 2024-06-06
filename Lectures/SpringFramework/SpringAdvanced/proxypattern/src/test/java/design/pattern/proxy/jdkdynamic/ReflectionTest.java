package design.pattern.proxy.jdkdynamic;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){

        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        //공통 로직1 종료


        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
        //공통 로직2 종료

    }

    @Test
    void reflection1() throws Exception{
        //클래스 정보 (? - 와일드 카드는 내가 모르는 타입을 받아올때 사용) - method의 표시는 $로 한다.
        Class<?> classHello = Class.forName("design.pattern.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        //callA 메서드 정보를 얻어보자 (callA, callB에 대한 method를 추상화 한 것이다.)
        Method methodCallA = classHello.getMethod("callA"); 
        //앞에 callA, callB로 박혀있는 부분을 문자로 바꿨다. 즉 필요할때마다 다르게 사용할수 있다.
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        //callB 메서드 정보를 얻어보자 
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result1={}", result2);
    }

    @Test
    void reflectionAppliced1() throws Exception{
        //클래스 정보 (? - 와일드 카드는 내가 모르는 타입을 받아올때 사용) - method의 표시는 $로 한다.
        Class<?> classHello = Class.forName("design.pattern.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        //추상화된다는 것은 이제 공통으로 사용할수 있다는 의미가 된다. (공통 인터페이스에 맞췄으니까 공통 처리가 가능해진다) - 마우스 모니터 등의 드라이버 예시
        Method methodCallA = classHello.getMethod("callA"); 
        dynmaicCall(methodCallA, target);

        //callB 메서드 정보를 얻어보자 
        Method methodCallB = classHello.getMethod("callB");
        dynmaicCall(methodCallB, target);
    }



    private void dynmaicCall(Method method, Object target) throws Exception{
        log.info("start");
        Object result1 = method.invoke(target);
        log.info("result1={}", result1);
    }

    @Slf4j
    static class Hello {
        public String callA(){
            log.info("callA");
            return "A";
        }

        public String callB(){
            log.info("callB");
            return "B";
        }
    }
    
}
