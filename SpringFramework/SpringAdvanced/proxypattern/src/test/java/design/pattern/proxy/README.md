## Package 순서 
* common - advice나 공통으로 사용될 service들이 들어있다.

* proxyfactory - 스프링이 프록시 패턴에 대한 기술을 추상화해놓은 것 (JDK 동적 프록시와 CGLIB)에 대해 소개한다. <br>
    * Interface를 target으로 하면 JDK를 불러오고 class를 tartget으로 하면 CGLIB을 불러온다.
<br><br>

----
### 동적 프록시 기술

1. pureproxy - proxy pattern에 대해 순수 Java로 어떻게 이루어져 있는지를 알수 있는 패키지 (근본) (Interface대상)
<br>

2. jdkdnymaic - java reflection을 이용해서 interface대상으로 어떻게 proxy를 동적으로 만들어주는지에 대해서 기술. <br>
    * TimeInvocationHandler - 이 구조로 프록시 객체가 만들어짐.
<br>

3. cglib - 동적으로 code를 generation해주지만, 구현체를 대상으로 proxy객체를 만들어준다. <br>
    * TimeMethodInterceptor - 이 구조로 프록시 객체가 만들어짐.
<br><br>

------------------------ 
### 스프링이 지원하는 프록시

4. advisor - Spring이 제공해주는 Advisor에 대해서 기술함. ProxyFactory에 Advisor, Advice, Pointcut을 어떻게 넣고 어떻게 사용하는지. (스프링이 CGLIB, Jdkdynmaic을 이용함.)
<br>
