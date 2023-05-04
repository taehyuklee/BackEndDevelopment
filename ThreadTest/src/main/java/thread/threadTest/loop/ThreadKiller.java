package thread.threadTest.loop;

public class ThreadKiller {
    public static void main(String[] args){
        Loop loop = new Loop();
        loop.start();
        try {
            Thread.sleep(1000);
            loop.stopThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
