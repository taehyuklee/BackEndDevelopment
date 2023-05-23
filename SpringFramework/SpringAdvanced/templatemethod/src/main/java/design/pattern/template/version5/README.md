## 템플릿 메소드 패턴을 적용했을때

<br>
아래와 같은 try catch와 같이 지저분한 code가 두 번째 블록의 코드로 바뀜 (간단해짐)

```java
//logging logic with controller
TraceStatus status = null;
try{
    //TraceStatus로 logging시작.
    status = traceService.begin("OrderController.request()");

    //파라미터 다 없애준다. 어차피 Singleton으로 관리되니까
    orderService.orderItem(itemId);

    //TraceStatus로 logging 마무리.
    traceService.end(status);

    return "ok";

}catch (Exception e){
    //logging시 예외처리
    traceService.exception(status, e);
    throw e;
}
```
<br>

```java
//이렇게 한줄로 끝남 (만들어 놓은 template에 넣어준다)
AbstractTemplate<String> template = new AbstractTemplate<String>(traceService) {

    @Override
    protected String call() {
        orderService.orderItem(itemId);
        return null;
    }
    
};

return template.execute("OrderController.request()");
```
<br>


Abstract class 부분은 아래와 같다.<br>

```java
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace){
        this.trace = trace;
    }

    //Logging Template
    public T execute(String message){
        TraceStatus status = null;

        try{
            //Logging 시작
            status = trace.begin(message);

            //핵심 로직 호출 (로직에 따라 변하는 부분)
            T result = call();

            //Logging 종료
            trace.end(status);
            return result;

        }catch(Exception e){
            trace.exception(status, e);
            throw e;
        }

    }

    protected abstract T call();
    
}

```