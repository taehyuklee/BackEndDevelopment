package design.pattern.strategy.version5.orderApp.domain.repository;


import org.springframework.stereotype.Repository;

import design.pattern.strategy.version5.trace.logtrace.LogTrace;
import design.pattern.strategy.version5.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;


@Repository("orderRepositoryV5")
@RequiredArgsConstructor
public class OrderRepository {

    private final LogTrace traceService;

    public void save(String itemId){

         //이렇게 한줄로 끝남 (만들어 놓은 template에 넣어준다)
         AbstractTemplate<Void> template = new AbstractTemplate<Void>(traceService) {

            @Override
            protected Void call() {
                //저장 로직
                if (itemId.equals("ex")){
                    throw new IllegalStateException("예외 발생!");
                }
                sleep(1000);
                return null;
            }
            
        };
        template.execute("OrderRepository.save()");

    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}
