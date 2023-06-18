### 각 버전별로의 의미.

1. AspectV1: AOP를 구현하기 위해 스프링 AOP 기술을 이용한 것, @Around를 통해 Pointcut과 Advice를 함꺼번에 표현 <br>

2. AspectV2: Pointcut을 분리해서 사용함. <br>
    ```java
    @Slf4j
    @Aspect //AspectV2는 Pointcut과 Advice를 나눠서 할 경우
    public class AspectV2 {

        //이렇게 PointCut을 만들면 다른곳에서도 이 PointCut을 사용할수 있게 된다.
        @Pointcut("execution(* spring.aop.order..*(..))")
        private void allOrder(){} //pointcut signature
        
        @Around("allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ 
            log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
            return joinPoint.proceed();
        }
    }
    ``` 
    <br><br>

3. AspectV3: 여러 PointCuts들을 이용해서 && || 조건 등을 사용해서 조합해서 사용하는 케이스. <br>

    ```java
    @Aspect
    @Slf4j
    public class AspectV3 {

        //이렇게 PointCut을 만들면 다른곳에서도 이 PointCut을 사용할수 있게 된다.
        @Pointcut("execution(* spring.aop.order..*(..))")
        private void allOrder(){} //pointcut signature

        @Pointcut("execution(* *..*Service.*(..))")
        private void allService(){}
        
        @Around("allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ 
            log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
            return joinPoint.proceed();
        }

        //order패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service인 경우
        @Around("allOrder() && allService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{ 
            
            try{
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                return result;
            }catch (Exception e){
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                throw e;
            }finally{
                log.info("[리소스 릴리즈] {}", joinPoint.getSignature()); 
            }
        }

        
    }
    ```
<br><br>

4. AspectV4Pointcut: 외부에서 Pointcut을 몰아넣고 사용하는 방식인 케이스를 보여주고있다. <br><br>


5. AspectV5Order: 여러 Adivce중 원하는 순서대로 적용하고 싶을때 어떻게 하는지에 대한 케이스를 얘기해준다.<br>

- 여기서 왜 중첩 클래스를 static으로 선언했는가? <br>

```
Spring AOP에서 중첩 클래스를 사용할 때 static 키워드를 사용해야 하는 이유는 Spring의 빈 인스턴스화 방식과 관련이 있습니다.

Spring은 빈으로 등록된 클래스를 프록시 객체로 감싸 AOP를 적용하는 방식을 사용합니다. 이 때 프록시 객체는 타겟 객체와 같은 타입을 가지면서도 부가적인 동작을 수행할 수 있도록 구현됩니다.

중첩 클래스가 static으로 정의되지 않으면, 해당 클래스는 외부 클래스의 인스턴스에 바인딩되어야 합니다. 하지만 Spring은 중첩 클래스의 인스턴스를 직접 생성하여 사용하는 것이 아니라, 클래스 레벨에서 정의된 타입을 통해 프록시 객체를 생성하고 관리합니다.

따라서 중첩 클래스가 static으로 정의되지 않으면, 중첩 클래스의 인스턴스화를 위해 외부 클래스의 인스턴스가 필요하게 됩니다. 그러나 중첩 클래스는 static이 아니기 때문에 외부 클래스의 인스턴스를 생성하지 않고는 중첩 클래스의 인스턴스를 생성할 수 없습니다. 따라서 Spring은 중첩 클래스를 빈으로 등록할 수 없게 되고, 에러가 발생하게 됩니다.

반면에 static으로 정의된 중첩 클래스는 외부 클래스의 인스턴스와 독립적으로 인스턴스화될 수 있습니다. 이는 중첩 클래스의 생성과 관리를 Spring이 담당할 수 있게 되며, 따라서 중첩 클래스를 빈으로 등록할 수 있습니다. 따라서 static으로 중첩 클래스를 정의하면 Spring AOP에서 정상적으로 사용할 수 있게 됩니다.
```
<br>