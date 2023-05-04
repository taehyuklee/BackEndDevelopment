package thread.threadTest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import thread.threadTest.service.ExecutorServiceTest;
import thread.threadTest.service.ThreadService;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final ThreadService threadService;
    private final ExecutorServiceTest executorServiceTest;

    @GetMapping("/stop")
    public void goThread() throws InterruptedException{
        threadService.threadStop();
    }

    @GetMapping("/start")
    public void startThread() throws InterruptedException{
        threadService.threadStop();
    }

    @GetMapping("/killer")
    public void goKiller() throws InterruptedException{
        threadService.threadKiller();
    }
    
    @GetMapping("/async")
    public void goAsync() throws InterruptedException{
        threadService.threadAsync();
    }

    @GetMapping("/threadPool")
    public void goTest() throws InterruptedException{
        executorServiceTest.threadPoolShutDown();
        
    }

    @GetMapping("/gcCollect")
    public void gcCollect() throws InterruptedException{
        threadService.gcCollect();
        
    }
}
