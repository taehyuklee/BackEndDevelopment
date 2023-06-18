package spring.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    
    //이렇게 PointCut을 만들면 다른곳에서도 이 PointCut을 사용할수 있게 된다.
    @Pointcut("execution(* spring.aop.order..*(..))")
    public void allOrder(){} //pointcut signature

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
    
}
