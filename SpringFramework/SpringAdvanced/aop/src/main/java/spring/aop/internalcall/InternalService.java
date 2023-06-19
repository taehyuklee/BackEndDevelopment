package spring.aop.internalcall;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalService {
    
    public void internal(){
        log.info("call ineternal");
    }

}
