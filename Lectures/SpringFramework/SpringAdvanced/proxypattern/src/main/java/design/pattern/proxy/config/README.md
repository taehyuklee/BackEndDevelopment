## Config에 대한 설명
- 프록시 패턴의 특성상 app부분의 service, controller, impl등은 건드리지 않고 부가기능을 입히기때문에, config부분만 바뀌게 된다 <br>

    1. v1_proxy: 순수 proxy를 적용했을때 - 자바만으로 프록시패턴, 또는 데코레이션패턴을 구현했을때의 케이스 <br>

    2. v2_dynamicproxy: jdk또는 cglib를 통해서 (Java reflection기반) 프록시를 만들때의 케이스 <br>

    3. v3_proxyfactory: 스프링에서 제공해주는 추상화된 개념의 proxyfactory를 이용해서 어떻게 구현하는지에 대한 케이스 <br>
    * 여전히 수동으로 프록시 객체 빈을 등록해야한다는 한계점이 있고 이것을 아래 빈 후처리기를 통해 컴포넌트 스캔에 의해 타겟이 등록될때 가로채서 프록시로 대신 저장하게 될 것<br><br>

    4. v4_postprocessor: 빈후처리기를 통해서 프록시를 자동으로 등록하게 하기 위한 내용. <br>

    5. v5_autoproxy: 스프링에서 pointcut만으로 프록시를 생성해주는 기능이 있다. 이에 대한 개념과 사용방법에 대해서 설명한다. - 어드바이저의 pointcut으로 자동 프록시 생성기 <br>
    * 결국 스프링도 위의 과정을 거쳐서 v5만으로 어드바이저안의 pointcut으로 자동으로 proxy객체를 만들수 있었다.<br>

    ```java
    public Advisor advisor3(LogTrace logTrace){
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* design.pattern.proxy.app..*(..)) && !execution(* design.pattern.proxy.app..noLog(..))");
        //패키지 기준으로 pointcut을 적용함. 그래서 특정 메소드에는 적용하고싶지 않으면 위와같이 해야한다. (noLog 제외)

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace); //advice LogTraceAdvice 앞서 만든것
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
    ```
    <br>

    6. v6_aop: v5_autoproxy도 위의 코드 조각과 같이 advice와 pointcut을 따로 만들어야 한다. 이조차도 간편하게 하고자 @Annotation으로 Advisor를 구현한 것이 있고 이에 대한 설명을 적시했다.

    <br><br>

- 결론: 위와 같이 Proxy 패턴을 사용하는 근본적인 이유는 핵심기능을 돕는 부가기능을 코드 전반에 걸쳐 필요한 곳에 공통으로 사용하기 위한 것이다. 이런 것을 'Cross-cutting concerns' 횡단 관심사라고 한다.