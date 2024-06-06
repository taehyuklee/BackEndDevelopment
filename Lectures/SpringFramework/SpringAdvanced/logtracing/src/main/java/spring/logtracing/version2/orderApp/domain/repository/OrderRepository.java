package spring.logtracing.version2.orderApp.domain.repository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import spring.logtracing.version2.trace.TraceId;
import spring.logtracing.version2.trace.TraceStatus;
import spring.logtracing.version2.trace.TraceService.TraceService;

@Repository("orderRepositoryV2")
@RequiredArgsConstructor
public class OrderRepository {

    private final TraceService traceService;

    public void save(TraceId traceId, String itemId){
        //저장 로직
        TraceStatus status = null;
        try{
            //TraceStatus로 logging시작
            status = traceService.beginSync(traceId, "OrderRepository.save()");

            //save core logic
            if (itemId.equals("ex")){
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);

            //시작된 TraceStatus로 logging 마무리
            traceService.end(status);

        }catch (Exception e){
            //logging시 예외처리
            traceService.exception(status, e);
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
