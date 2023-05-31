package design.pattern.proxy.pureproxy.concreteproxy.code;

public class ConcreteClient {
    
    private ConcreteLogic concreteLogic; //Client에 구체 클래스를 받아옴 (인터페이스가 아님)

    public ConcreteClient(ConcreteLogic concreteLogic){
        this.concreteLogic = concreteLogic;
    }

    public void execute(){
        concreteLogic.operation();
    }

}
