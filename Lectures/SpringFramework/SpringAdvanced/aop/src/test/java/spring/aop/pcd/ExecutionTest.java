package spring.aop.pcd;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import lombok.extern.slf4j.Slf4j;
import spring.aop.member.MemberServiceImpl;

@Slf4j
public class ExecutionTest {
    //Execution으로 pointcut
    
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach //test할때마다 method를 뽑아다가 helloMethod에 넣어줄거야
    public void init() throws NoSuchMethodException, SecurityException{
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod(){
        //public java.lang.String spring.aop.member.MemberServiceImpl.hello(java.lang.String) <-이걸 봤을때 기본적으로 자바 Reflection을 이용해서 하네
        log.info("helloMethod={}", helloMethod);
    }

    @Test //완전히 정확히 matching시켜서 하는것.
    void exactMatch(){
        //public java.lang.String spring.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String spring.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    //가장 많이 생략된 pointcut (반환타입 -> *, 메서드 이름 -> *, 파라미터 => (..))
    @Test
    void allMatch(){
        pointcut.setExpression("execution(* *(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //method 이름으로 matching해보자
    @Test
    void nameMatch(){
        pointcut.setExpression("execution(* hello(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1(){
        pointcut.setExpression("execution(* hel*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2(){
        pointcut.setExpression("execution(* *el*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse(){
        pointcut.setExpression("execution(* nono(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    /* Package type으로 */
    @Test
    void packageExactMatch1(){
        pointcut.setExpression("execution(* spring.aop.member.MemberServiceImpl.hello(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2(){
        pointcut.setExpression("execution(* spring.aop.member.*.*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test //package까지는 정확히 맞춰줘야한다. 즉 memeber까지는 맞춰줘야 한다. 
        //즉, package아래 type을 *로 표시하는거지 하위 패키지를 *로 표현하지는 않는다는 의미임 여기서는 (subpackage까지 포함하는건 ..으로 해야함 )
    void packageExactFalse(){
        pointcut.setExpression("execution(* spring.aop.*.*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //하위 패키지관련
    @Test 
    void packageMatchSubPackage1(){
        pointcut.setExpression("execution(* spring.aop.member..*.*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test 
    void packageMatchSubPackage2(){
        pointcut.setExpression("execution(* spring.aop..*.*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /*여기서부터는 type관련해서 matching을 해보자 */
    @Test 
    void typeExactMatch(){
        pointcut.setExpression("execution(* spring.aop.member.MemberServiceImpl.*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

   @Test //과연 부모타입으로 검색해도 이게 검색이 되는것일까? (되넹?)
    void typeExactMatchSuperType(){
        pointcut.setExpression("execution(* spring.aop.member.MemberService.*(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

   @Test //부모타입에서 선언해놓았던 method까지만 매칭이되고 그 외의 것들은 매칭이 되지 않는다. (즉, 어드바이저를 생성할수 없다 자식타입만의 메서드는)
    void typeExactMatchInternal() throws NoSuchMethodException, SecurityException{
        pointcut.setExpression("execution(* spring.aop.member.MemberService.*(..)))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    /*파라미터 매칭관련한 것을 해볼 것 */
    //(String)
    @Test
    void argsMatch(){
        pointcut.setExpression("execution(* *(String)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //파라미터가 없는 경우
    //()
    @Test
    void argsMatchNoArgs(){
        pointcut.setExpression("execution(* *()))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    //정확히 하나의 파라미터 허용, 모든 타입 허용
    //(Xxx)
    @Test
    void argsMatchStar(){
        pointcut.setExpression("execution(* *(*)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }    


    //숫자와 무관하게 모든 파라미터, 모든 타입 허용
    //(Xxx), (Xxx, Xxx)
    @Test
    void argsMatchAll(){
        pointcut.setExpression("execution(* *(..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }    


    //숫자와 무관하게 모든 파라미터, 모든 타입 허용
    //(String), (String, Xxx), (String, Xxx, Xxx)
    @Test
    void argsMatchComplex(){
        pointcut.setExpression("execution(* *(String, ..)))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }    
}
