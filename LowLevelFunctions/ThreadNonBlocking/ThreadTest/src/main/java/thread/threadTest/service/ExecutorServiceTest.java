package thread.threadTest.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExecutorServiceTest{

    ExecutorService executor = Executors.newFixedThreadPool(4);

    private final ThreadService threadService;


    public void threadPoolShutDown() throws InterruptedException{
        // executor.submit(() -> {
        //     try {
        //         threadService.threadAsync();
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // });


        Future<?> future = executor.submit(() -> {
            try {
                threadService.threadAsync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("future객체로 강제 종료");
        future.cancel(true);
        future = null;

        System.out.println(executor.isShutdown());
        System.out.println("강제 종료 시도");
        executor.shutdownNow();

    }
    //만료일자 
    //core가 넘어가는지 확인 
    //액추에이터 -> 액추에이터 스프링부트 라이브러리 -> 씨피유 메모리 등 다 가져올수 있다. 쏠때마다
    //라이센스 헬스체크 -> 모든 에이피 아이 게이트웨이에 다 쏘고 메모리에 저장해 놓고 있는다. 파일로 떨궈 놓는다. 
    //떨궈 놓은거 가지고 무결성 체크 하고

    
}
