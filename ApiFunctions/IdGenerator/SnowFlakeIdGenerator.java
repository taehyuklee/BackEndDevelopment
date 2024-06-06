import java.time.LocalDateTime;
import java.time.ZoneId;

public class SnowFlakeIdGenerator {
	
	private final long twEpoch = 1288834974657L;
	// SnowFlake기법에서는 Timestamp 41Bit, 서버 Id 5Bit, DC ID 5Bit, SerialNum 12Bit로 구성되어 있다.
	
	private final long timestampBits = 41L;
	private final long serverIdBits = 5L;
	private final long datacenterIdBits = 5L;
	private final long serialBits = 12L;
	 
	 
	// 비트 시프트 (경계들만 만들어 놓고 나중에 비트 자리로 움직여줘야 하니까)
	private final long serialIdShiftFromRight = serialBits; // 왼쪽으로부터 12자리(Bit)까지가 SerialNum자리
	private final long datacenterIdShiftFromRight = serialBits + serverIdBits; // 왼쪽에서 17부터는 데이터 센터 Id 자리 
	private final long timestampLeftShiftFromRight = serialBits + serverIdBits + datacenterIdBits; // 왼쪽에서 22부터는 41자리까지 timestamp자리
	private final long signLeftShiftFromRight = serialBits + serverIdBits + datacenterIdBits + timestampBits;
	
	// 기준이되는 값을 관리한다. serial이 한 바퀴 돌았을때 마스킹한다던지
	private final long serialMask = -1L ^ (-1L << serialBits);
	 
	// 초기 stamp값
	private long oldTimestamp = -1L;
	private long serialNum = 1L;
	
	
	// Generate Next Id (SerialNum 동시성 처리 문제만 확인하면)
	public synchronized long generateNewId(long serverId, long dataCenterId) throws Exception {
		
		//현재 시각 milliSeconds로 얻고 binary로 변환
		long timestamp = getCurrentTimeStamp();
		
	    //여기서 생각을 그냥 비트 연산한다고 생각해야 한다. 그냥 Long Type(64bit 박아놓고)
	    long signedId = 1L;
	    long timestampId = timestamp - twEpoch;
	
	    
	    //Serial Number 증가 
	    if(timestamp == oldTimestamp) {
	    	
	    	serialNum = (serialNum + 1) & serialMask;
	    	
	    	//만약 2^11 -> 4096개 Serial Num를 다 사용하게 된다면?
	    	if( serialNum ==0 ) {
	    		long newTime = getCurrentTimeStamp();
	    		while(timestamp != newTime) {
	    			newTime = getCurrentTimeStamp();
	    		}
	    	}
	    	
	    }else {
	    	//달라지면 초기화 해준다.
	    	serialNum = 1L; 
	    }
	    
	    
	    
	    //이전에 기록했던 시간과 비교하기 위
	    oldTimestamp = timestamp;
	    
	    
	    //Bit연사자로 칸수를 띄우면서 만들기 
	    long snowFlakeId = 
	    		signedId << signLeftShiftFromRight |
	    		timestampId << timestampLeftShiftFromRight | 
	    		dataCenterId << datacenterIdShiftFromRight |
	    		serverId << serialIdShiftFromRight |
	    		serialNum;
	    
	    
	    return snowFlakeId;
		
	}
	
	// Get TimeStampe
	private long getCurrentTimeStamp () {
		
	    //사용할 zone 아이디 값.
	    ZoneId zoneid = ZoneId.of("Asia/Seoul");
	
	    //#1. 현재 시간의 값 밀리세컨드 변환
	    long timestamp = LocalDateTime.now().atZone(zoneid).toInstant().toEpochMilli();
	    
	    return timestamp;
		
	}
	
	// TimeStamp to Bit
	private String timeStampToBit(long timestamp) {
		
		//변환
		String timeBinary = Long.toBinaryString(timestamp);
		
		//Console 출력
		System.out.println("현재 시각 milliSeconds는 다음과 같습니다: " + timestamp);
		System.out.println("현재 시각(milliseconds)를 Binary로 바꾸면 다음과 같습니다: " + timeBinary);
		System.out.println("Binary의 길이는 다음과 같습니다: " + timeBinary.length());
	    
	    return timeBinary;
	}
	
	
	// Bit to TimeStamp
	private long bitToTimeStamp(String binaryTime) {
		
		// Binary로 되어 있던걸 다시 timestamp로 변환
		long timestamp = Long.parseLong(binaryTime, 2); 
	    
	    System.out.println("Binary를 역변환하면 다음과 같습니다: " + timestamp);
	    
	    return timestamp;
		
	}

}
