package spring.logtracing.version3.trace.logtrace;

import lombok.extern.slf4j.Slf4j;
import spring.logtracing.version3.trace.TraceId;
import spring.logtracing.version3.trace.TraceStatus;


@Slf4j
public class FieldLogTrace implements LogTrace{


    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<x-";

    private TraceId traceIdHodler; //traceId 동기화, 동시성 이슈 발생할수 있다.


    @Override
    public TraceStatus begin(String message) {

        syncTraceId();

        TraceId traceId = traceIdHodler;
        Long startTimeMs = System.currentTimeMillis();
        

        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        //로그 출력
        return new TraceStatus(traceId, startTimeMs, message);
    }


    //Sync라는게 depth로 계속 들어갈대 level을 높여줘야 하는거다보니까 syncTraceId()자체가 
    private void syncTraceId(){
        if (traceIdHodler == null){
            //최초 호출에는 새로 만들고
            traceIdHodler = new TraceId();
        }else{
            //직전 로그가 있으면, level(depth)이 1 증가한다.
            traceIdHodler = traceIdHodler.createNextId();
        }
    }



    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }



    @Override
    public void exception(TraceStatus status, Exception e) {
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

        releaseTraceId();

    }

    //Sync라는게 depth를 밖으로 빠져나올때, 사용하는거
    private void releaseTraceId(){
        if(traceIdHodler.isFirstLevel()){
            traceIdHodler = null; //destroy
        }else{
            traceIdHodler = traceIdHodler.createPreviousId();
        }
    }


    private static String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<level; i++){
            sb.append((i==level-1) ? "|" + prefix: "|   ");
        }
        return sb.toString();
    }
    
}
