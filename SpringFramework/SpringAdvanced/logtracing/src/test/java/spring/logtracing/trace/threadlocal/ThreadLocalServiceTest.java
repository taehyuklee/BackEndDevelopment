package spring.logtracing.trace.threadlocal;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import spring.logtracing.trace.threadlocal.code.ThreadLocalService;

@Slf4j
public class ThreadLocalServiceTest {
    
    //동시성 문제 test
    private ThreadLocalService threadLocalService = new ThreadLocalService();

    @Test
    void field(){
        log.info("main start");

        //ThreadA class 정의
        Runnable userA = () -> {
            threadLocalService.logic("userA");
        };

        //ThreadB class 정의
        Runnable userB = () -> {
            threadLocalService.logic("userB");
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
