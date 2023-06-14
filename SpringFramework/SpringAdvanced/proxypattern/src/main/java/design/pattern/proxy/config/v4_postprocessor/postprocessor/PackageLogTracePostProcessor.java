package design.pattern.proxy.config.v4_postprocessor.postprocessor;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor{
    
    //특정 패키지에만 적용할 것이다. (진짜 외부에서 다 받아온다)
    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor){
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    //기존의 target bean이 다 만들어지고 나서 프록시를 등록할거기때문에 (빈 등록 이후에 사용할수 있는 "postProcessAfterInitialization"를 사용할 것)
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        log.info("param beanName={} bean={}", beanName, bean.getClass());       

        //프록시 적용 대상 여부 체크//

        //프록시 적용 대상이 아니면 원본을 그대로 진행 (target을 그대로 등록)
        //Package package = bean.getClass().getPackage(); //Package객체가 존재하네 이 패키지 객체는 뭘까?
        String packageName = bean.getClass().getPackageName();
        if(!packageName.startsWith(basePackage)){
            //내가 원하는 package인 app package이외의 것들이 등록되면 그대로 적용한다
            return bean;
        }

        //프록시 대상이면 프록시를 만들어서 반환 
        ProxyFactory proxyFactory = new ProxyFactory(bean); //target에 bean이 들어가게 된다.
        proxyFactory.addAdvisor(advisor);
        Object proxy = proxyFactory.getProxy();//Object자체를 반환해주면 spring에서는 알아서 proxy로 저장된다.
        log.info("create proxy: target={} proxy={}", bean.getClass(), proxy.getClass());

        return proxy;
    }

}
