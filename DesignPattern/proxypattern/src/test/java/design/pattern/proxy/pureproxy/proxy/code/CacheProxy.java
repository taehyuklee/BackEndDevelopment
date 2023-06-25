package design.pattern.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

//CacheProxy에서는 

@Slf4j
public class CacheProxy implements Subject{

    private Subject target; //실제 넣을 객체를 target이라 한다.
    private String cacheValue;

    public CacheProxy(Subject target){
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");

        if(cacheValue ==null){ //첫 조회에서만 target에서 값을 가져오고 cacheValue에 넣어둔다.
            cacheValue = target.operation();
        }

        return cacheValue; //두 번재 호출부터는 cacheValue에 저장된 것을 바로 가져온다.
    }

}
