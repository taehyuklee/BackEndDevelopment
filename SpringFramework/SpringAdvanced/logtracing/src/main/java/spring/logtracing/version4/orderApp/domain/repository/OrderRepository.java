package spring.logtracing.version4.orderApp.domain.repository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version4.trace.TraceId;
import spring.logtracing.version4.trace.TraceStatus;
import spring.logtracing.version4.trace.logtrace.LogTrace;

@Repository("orderRepositoryV4")
@RequiredArgsConstructor
public class OrderRepository {

    private final LogTrace tracesService;

    public void save(TraceId traceId, String itemId){
        //저장 로직
        TraceStatus status = null;
        try{
            //TraceStatus로 logging시작
            status = tracesService.begin("OrderRepository.save()");

            //save core logic
            if (itemId.equals("ex")){
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);

            //시작된 TraceStatus로 logging 마무리
            tracesService.end(status);

        }catch (Exception e){
            //logging시 예외처리
            tracesService.exception(status, e);
            throw e;
        }

    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}