
public class Main {
	
	public static void main(String[] args) {
		
		MyPublisher Mono = new MyPublisher();
		
		MySubscriber mySubscriber = new MySubscriber();

		
		String[] input = {"a", "b", "c", "d", "e"};
	
		//Subscriber를 Publisher에 등록
		System.out.println("post Mono");
		Mono.just(input).subscribe(mySubscriber);
		System.out.println("after Subscribe");
		
		mySubscriber.onNext("your Item");
		
		//String[] input = {"a", "b", "c", "d", "e"};
		
		

		

	}

}
