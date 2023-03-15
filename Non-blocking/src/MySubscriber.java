import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscriber implements Subscriber<String> {
    private Subscription subscription;
 
    @Override
    public void onSubscribe(Subscription subscription) {
        // Publisher에서 전달받은 Subscription 객체를 저장합니다.
        this.subscription = subscription;
        // 데이터를 요청합니다.
        //subscription.request(1);
        System.out.println("onSubscribe");
    }
 
    @Override
    public void onNext(String item) {
        // Publisher에서 데이터를 전달받았을 때 호출되는 메서드입니다.
        System.out.println("onNext: " + item);
        // 다음 데이터를 요청합니다.
        subscription.request(1);
    }
 
    @Override
    public void onError(Throwable throwable) {
        // 에러가 발생했을 때 호출되는 메서드입니다.
        System.out.println("onError: " + throwable.getMessage());
    }
 
    @Override
    public void onComplete() {
        // 모든 데이터를 전달받았을 때 호출되는 메서드입니다.
        System.out.println("onComplete");
    }
}