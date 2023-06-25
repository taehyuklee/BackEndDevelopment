### 위 Package는 템플릿 메소드 패턴에 관련된 것을 설명한다. 
<br>

문제점
```java
try{

    logging 시작

    //핵심로직
    businessLogic();

    logging 종료


}catch(Exception e){

    log.info(e.toString());

}

```
위와 같이 logging하는 부분이나 try catch와 같이 똑같은 패턴으로 반복되는(변하지 않는) 부분이 모든 소스에 필요하고 변하는 부분은 핵심로직만 변할때, 
사용 가능한 디자인 패턴이 바로 템플릿 메소드 패턴이다. 변하지 않는 부분을 template화하고 변하는 핵심 로직 부분만 추상 메소드를 통해서 변경하는 디자인 패턴이다.<br><br>

### <b>핵심</b> - 변하지 않는 template부분을 부모 Class로 (abstract Class - 템플릿은 일부 구현해야 하니까), 변하는 핵심로직 부분을 template을 상속받은 자식 class로
<br>

```java
//부모 Class (Template 부분)
public abstract class AbstractTemplate {
    
    //template 부분
    public void execute(){
        long startTime = System.currentTimeMillis();

        //핵심 비즈니스 로직 실행
        call(); //상속 (자식 class에 따라서 이 부분이 바뀐다)
        //핵심 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    //자식 class에서 call을 구현해서 넣어준다.
    protected abstract void call();
}


```
<br>

```java
//자식 Class (변하는 핵심 로직 부분)
@Slf4j
public class SubClassLogic1 extends AbstractTemplate{

    @Override
    protected void call() {
        log.info("비즈니스 로직1 실행");
    }
    
}
```

<br>

```java
//결국 아래와 같이 try cath할 필요 없이 핵심 로직만 넣어수 구현이 가능해진다.
@Test
void templateMethodV1(){
    AbstractTemplate template1 = new SubClassLogic1();
    template1.execute();

    AbstractTemplate template2 = new SubClassLogic2();
    template2.execute();
}
```

<br>
위에서 1000개의 try-catch가 있다면, 모두 고칠필요 없이 템플릿에서 하나만 고치면 1000개 모두를 수정할수 있다.

<br><br>

### 템플릿 메서드 패턴의 단점 
1. 자식 클래스가 부모 클래스(템플릿)를 상속받기 때문에, 자식 클래스는 원하던 원치 않던 부모 클래스의 기능을 가져와야 한다. 설사, 그 기능을 이용하지 않는다고 하더라도. 
즉, 컴파일 시점에 강하게 결합되어 있기 때문에 의존도가 매우 높으며, 자식 클래스가 부모 클래스에 영향을 많이 받는다. 

2. 템플릿 메서드 패턴은 상속 구조를 사용함으로써, 별도의 클래스나 익명 내부 클래스를 만들어야 한다.

<br>
* 이러한 단점을 제거할수 있는 디자인 패턴이 바로 전략 패턴(<b>Strategy Pattern</b>)이다.