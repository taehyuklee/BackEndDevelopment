package thread.threadTest.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadService{

    
    public void threadStop() throws InterruptedException{

        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            try {
                boolean a = true;
                while (!Thread.currentThread().isInterrupted()) {
                    if(a){
                        threadAsync();
                    }
                    Thread.sleep(5000);
                    a = false;
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        System.out.println(Thread.currentThread().getName());

        long end;

        while(true){
            end = System.currentTimeMillis();
            if(end-start>5000){
                thread.stop(); //deprecated 되어 있는 것(유일한 방법)
                break;
            }
            
        }
        System.out.println();
        System.out.println("프로세스가 종료되었습니다");

    }
    
    public void threadKiller(){

        Thread thread = new Thread(() -> {
            try {
                boolean a = true;
                while (!Thread.currentThread().isInterrupted()) {
                    if(a){
                        threadAsync();
                    }
                    Thread.sleep(5000);
                    a = false;
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        

        // 일정시간 지나면 현재 Thread 를 종료
         Thread killerThread = new Thread() {
            
            @Override
            public void run() {
                try {
                    // 1분 후 종료
                    System.out.println("start");
                    thread.interrupt();
                    Thread.sleep(3000);
                    
                } catch (InterruptedException e) {
                    // 킬러 Thread 종료(killerThread.interrupt())하면 이곳에 도달
                    System.out.println("프로세스 종료");
                    return;
                    
                } catch (Exception e) {
                    // 무시
                }
                
                try {
                    // 일정시간이 지나면 이곳에 도달
                    System.out.println("시간초과로 인해 종료합니다.");
                    
                    // 현재 Thread 를 종료
                    thread.interrupt();
                    
                } catch (Exception e) {
                    System.out.println("interrupted");
                    // 무시
                }
            }
        };
        
        System.out.println("KillerThread 작동 시작");
        killerThread.start();


    }

    public void gcCollect() throws InterruptedException{


        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            try {
                boolean a = true;
                while (!Thread.currentThread().isInterrupted()) {
                    if(a){
                        threadAsync();
                    }
                    Thread.sleep(5000);
                    a = false;
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        System.out.println(Thread.currentThread().getName());

        long end;

        while(true){
            end = System.currentTimeMillis();
            if(end-start>5000){
                thread = null;
                // while(!thread.isAlive()){
                    Thread.sleep(5000);
                    System.gc();
                // }
                break;

            }
            
        }

        System.out.println("break");

    }


    @Async("hndlrThreadExecutor")
    public void threadAsync() throws InterruptedException{
        System.out.println("hi");

        long start = System.currentTimeMillis();
        long end;
        int size=0;
        while(true){
            end = System.currentTimeMillis();
            if((end-start)%5000L==0){
                System.out.println(size++);
            }
        }

    }
    
}