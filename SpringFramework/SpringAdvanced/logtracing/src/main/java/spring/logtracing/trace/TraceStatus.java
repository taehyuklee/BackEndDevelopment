package spring.logtracing.trace;

//log의 상태정보를 나타낸다. (시작할때의 상태 정보)
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message){
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return this.traceId;
    }

    public Long getStartTimeMs() {
        return this.startTimeMs;
    }

    public String getMessage() {
        return this.message;
    }


    
}
