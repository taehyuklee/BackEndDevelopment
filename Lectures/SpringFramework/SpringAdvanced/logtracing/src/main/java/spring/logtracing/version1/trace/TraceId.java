package spring.logtracing.version1.trace;

import java.util.UUID;

public class TraceId {

    private String id; //Transaction Id가 된다. [ex) 796bccd9]
    private int level; //depth를 나타낸다 |--> 에 관한거

    /* ------------------------------- 생성자 관련 method ------------------------------------- */
    //Default Constructor (기본 생성자)
    public TraceId(){
        this.id = createId();
        this.level =0;
    }

    //Constructor Injection (생성자 주입)
    private TraceId(String id, int level){
        this.id = id;
        this.level = level;
    }
    
    //기본 생성자에서는 자동으로 UUID를 만들어준다.
    private String createId(){
        //UUID 앞자리 8자리만 잘라서 만든다.
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /* ------------------------------- level관련 method ------------------------------------- */
    //
    /* traceId는 같고 그 아래 depth가 하나 증가한다. 즉 controller에서 service로 넘어갈때 depth가 증가 (이걸 표현하기 위함)
     * [b7119f27] OrderController.request()
     * [b7119f27] |-->OrderService.orderItem()
     */

    public TraceId createNextId(){ //
        return new TraceId(id, level+1);
    }

    public TraceId createPreviousId(){
        return new TraceId(id, level-1);
    }

    public boolean isFirstLevel(){
        return level ==0;
    }

    /* ------------------------------- Getter & Setter ------------------------------------- */

    public String getId(){
        return this.id;
    }

    public int getLevel(){
        return level; 
    }
    
}
