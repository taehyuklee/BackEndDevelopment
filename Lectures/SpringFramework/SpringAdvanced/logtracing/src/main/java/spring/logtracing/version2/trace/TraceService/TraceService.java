package spring.logtracing.version2.trace.TraceService;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import spring.logtracing.version2.trace.TraceId;
import spring.logtracing.version2.trace.TraceStatus;


@Slf4j
@Component("traceServiceV2")
public class TraceService {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<x-";

    //log가 시작할때는 begin을 호출해주고  결과: [796bccd9] OrderController.request() //로그 시작
    public TraceStatus begin(String message){

        //현재 상태를 표현해주기 위한 TraceStatus를 만들어주는 과정.
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        
        /*log.info에서는 {}에 그 뒤에 인자들이 자동으로 들어가게 해놨네, 편하네 (실제로 구현하려면 replace써야되는데)
        * 첫번째 인자에는 Transaction ID, 두번째 인자에는 | 세번째 인자에는 --> 
        */
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        //로그 출력
        return new TraceStatus(traceId, startTimeMs, message);
    }

    //두번째 level부터는 beginSync로 호출한다.
    public TraceStatus beginSync(TraceId beforeTraceId, String message){

        //level만 이전 TraceId에서 createNextId를 함으로써 depth를 1증가시킨 (id는 유지) TraceId를 가져온다. (domain에서 해당 객체관련 method를 같이 넣어둔다)
        TraceId nextId = beforeTraceId.createNextId(); 
        Long startTimeMs = System.currentTimeMillis();

        log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);

        //로그 출력
        return new TraceStatus(nextId, startTimeMs, message);
    }

    //log가 종료될때는 end method를 호출해주면 된다. 결과: [796bccd9] OrderController.request() time=1016ms //로그 종료
    public void end(TraceStatus status){
        complete(status, null);
    }

    //exception을 만든다 (예외가 터질때). 결과: [b7119f27] | |<X-OrderRepository.save() time=0ms
    public void exception(TraceStatus status, Exception e){
        complete(status, e);
    }


    /*------------------------------------------- 공통 method -------------------------------------- */
    //end랑 exception에서 사용할 공통 method
    private void complete(TraceStatus status, Exception e){
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if(e ==null){
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()),  status.getMessage(), resultTimeMs);
        }else{
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

    }

    //level=0 
    //level=1 |-->
    //level=2 |   |-->

    //level=2 (exception) |   |<x-
    //level=1 (exception) |<x-
    private static String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<level; i++){
            sb.append((i==level-1) ? "|" + prefix: "|   ");
        }
        return sb.toString();
    }


    
}
