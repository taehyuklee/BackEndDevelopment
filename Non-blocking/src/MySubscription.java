import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscription implements Subscription {
    private final Subscriber<? super String> subscriber;
    private final String[] data;
    private int currentIndex = 0;
 
    public MySubscription(Subscriber<? super String> subscriber, String[] data) {
        this.subscriber = subscriber;
        this.data = data;
    }
 
    @Override
    public void request(long n) {
        // 데이터를 요청할 때 호출되는 메서드입니다.
        for (int i = 0; i < n; i++) {
            if (currentIndex >= data.length) {
                subscriber.onComplete(); // 데이터를 모두 발행한 경우 onComplete를 호출합니다.
                return;
            }
            subscriber.onNext(data[currentIndex++]); // 데이터를 발행합니다.
        }
    }
 
    @Override
    public void cancel() {
        // 구독 취소할 때 호출되는 메서드입니다.
        System.out.println("Subscription cancelled");
    }
}