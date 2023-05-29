package design.pattern.proxy.version5.orderApp.domain.repository;


import org.springframework.stereotype.Repository;

import design.pattern.proxy.version5.trace.logtrace.LogTrace;
import design.pattern.proxy.version5.trace.template.TraceTemplate;


@Repository("orderRepositoryV5")
public class OrderRepository {

    private final TraceTemplate template;

    public OrderRepository(LogTrace trace){
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId){

        template.execute("OrderRepository.save()", ()->{
            //저장 로직
            if (itemId.equals("ex")){
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            return null;
        });
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}
