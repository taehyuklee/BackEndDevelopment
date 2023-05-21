package hello.core.singleton;

public class SingletonService {
    
    //아래와 같이 static 영역에 자기 자신의 객체를 생성해 놓으면 class영역에 올라가기 때문에 하나만 올라가게 된다.
    private static final SingletonService instance = new SingletonService();
    
    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService(){
        //밖에서 new SingletonService()를 해서 새로 객체를 만들수 있으니까 생성자 자체를 private으로 막아버림
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
    
}
