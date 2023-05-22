package spring.logtracing.trace.threadlocal;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import spring.logtracing.trace.threadlocal.code.FieldService;

@Slf4j
public class FieldServiceTest {

    //동시성 문제 test

    private FieldService fieldService = new FieldService();

    @Test
    void field(){
        log.info("main start");

        //ThreadA class 정의
        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        //ThreadB class 정의
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100);
        threadB.start();

        sleep(2000);
        log.info("main exit");
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
