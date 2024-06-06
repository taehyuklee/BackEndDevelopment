import java.util.concurrent.Flow.*;
 
public class MyPublisher implements Publisher<String> {
    static private String[] data = new String[100];
 
    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        // 구독할 때 호출되는 메서드입니다.
        subscriber.onSubscribe(new MySubscription(subscriber, data));
    }
    
    public MyPublisher just(String[] inputData) {
    	data = inputData;
    	
    	return this;
    }
}
