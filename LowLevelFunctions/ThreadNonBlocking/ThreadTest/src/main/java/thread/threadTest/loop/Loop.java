package thread.threadTest.loop;

public class Loop extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("쓰레드 시작");
            while(true) {
                Thread.sleep(0);
                System.out.println("무한루프");
            }
        } catch (InterruptedException e) {
            System.out.println("인터럽트로 인한 스레드 종료.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("스레드 종료.");
    }
    public void stopThread() {
        super.interrupt();
    }
}