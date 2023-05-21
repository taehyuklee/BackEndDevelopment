package hello.core.lifecycle;

public class TestInterfaceImpl implements TestInterface{

    public TestInterfaceImpl(){
        System.out.println("Constructor TestInter");
    }

    @Override
    public void great() {
        System.out.println("great");
    }

    @Override
    public void go() {
        System.out.println("go");

    }
}
