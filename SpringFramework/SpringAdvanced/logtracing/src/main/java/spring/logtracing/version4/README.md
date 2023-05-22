ThreadLocal을 적용하여, 동시성문제를 해결한 version입니다.

ThreadLocalLogTrace.java를 보면 알수 있습니다.

그리고 config에서 기존 Version3에서되어 있었던

```java
public LogTrace logTrace(){
    //singleton으로 등록함.
    return new FieldLogTrace();
}
```

이 부분을, 다음과 같이만 바꿔주면 끝이다 (스프링 interface에 구현체만 바꿔서 정말 oop적이다)

```java
public LogTrace logTrace(){
    //singleton으로 등록함.
    return new ThreadLocalLogTrace();
}
```
<br>

## ThreadLocal 사용시 주의사항

  ThreadLocal을 사용할때 주의사항이 있다.
Tomcat WAS는 ThreadPool을 사용하는데, 예를 들어 thread Pool에서 threadA를 가져다 썼다고 해보자, 그리고 thread-A의 ThreadLocal에 일부 내용을 저장하고 지우지 않았다고 가정해보자
자 여기서, thread-A를 다 사용하고 나면 다시 스레드풀에 넣어줘야 한다.
그런데, Thread-A가 제거된게 아니라 아직 살아있는 것이기에, 스레드풀에 그냥 있고 Stack memory(자기 고유 영역)에 저장된 내용은 그대로 남아있다.

이후 Thread-A를 재사용하려고 꺼냈더니 ThreadLocal에 내용이 그대로 남아있는 경우가 생겨버린다. (다른 사용자의 내용물을 볼수 있게 된다.)