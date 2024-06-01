import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class IdGenerator {
	
	 private static final long twEpoch = 1288834974657L;
	 
	 // SnowFlake기법에서는 Timestamp 41Bit, 서버 Id 5Bit, DC ID 5Bit, SerialNum 12Bit로 구성되어 있다.
	 private static final long timestampBits = 41L;
     private static final long serverIdBits = 5L;
     private static final long datacenterIdBits = 5L;
     private static final long serialBits = 12L;
	 
	 
     // 비트 시프트 (경계들만 만들어 놓고 나중에 비트 자리로 움직여줘야 하니까)
     private static final long serialIdShiftFromRight = serialBits; // 왼쪽으로부터 12자리(Bit)까지가 SerialNum자리
     private static final long datacenterIdShiftFromRight = serialBits + serverIdBits; // 왼쪽에서 17부터는 데이터 센터 Id 자리 
     private static final long timestampLeftShiftFromRight = serialBits + serverIdBits + datacenterIdBits; // 왼쪽에서 22부터는 41자리까지 timestamp자리
     private static final long signLeftShiftFromRight = serialBits + serverIdBits + datacenterIdBits + timestampBits;

     // 기준이되는 값을 관리한다. serial이 한 바퀴 돌았을때 마스킹한다던지
     private static final long maxServerId = -1L ^ (-1L << serverIdBits);
     private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
     private static final long serialMask = -1L ^ (-1L << serialBits);
	

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		StringBuilder sb = new StringBuilder();
		
		//현재 시각 milliSeconds로 얻고 binary로 변환
		long timestamp = getCurrentTimeStamp();
        String binaryTimeStamp = timeStampToBit(timestamp);

        //확인작업 (다시 timeStampe로 바꿨을때 잘 나오는지 확인)
        bitToTimeStamp(binaryTimeStamp);

        //여기서 생각을 그냥 비트 연산한다고 생각해야 한다. 그냥 Long Type(64bit 박아놓고)
        long signedId = 1L;
        long timestampId = timestamp - twEpoch;
        long dataCenterId = 1L;
        long serverId = 1L;
        long serialNum = 1L;
        
        long snowFlakeId = 
        		signedId << signLeftShiftFromRight |
        		timestampId << timestampLeftShiftFromRight | 
        		dataCenterId << datacenterIdShiftFromRight |
        		serverId << serialIdShiftFromRight |
        		serialNum;
        
        System.out.println("snowFlakeId는: " + snowFlakeId);
        
        System.out.println(Long.toBinaryString(snowFlakeId));
        System.out.println(Long.toBinaryString(snowFlakeId).length());
        
        
        System.out.println();
       
        //처음에는 비트연산자를 쓸수 없어서 그냥 String으로 다 더해서변환할 생각을 했다. 
        
        
        
        
        
        System.out.println(Long.toBinaryString(twEpoch));
        
        Date timeInDate = new Date(twEpoch);
        String timeInFormat = sdf.format(timeInDate);
        
        System.out.println(timeInFormat);
        
        String last41 = "11111111111111111111111111111111111111111";
        

        int a = 5;
		
		String b = Integer.toBinaryString(a);
		
		System.out.println(b);
		
		String c = "1001";
		
		int d = 1001;
		
		System.out.println(Integer.toString(d));
		
		

		
	}
	
	// Get TimeStampe
	private static long getCurrentTimeStamp () {
		
	    //사용할 zone 아이디 값.
        ZoneId zoneid = ZoneId.of("Asia/Seoul");

        //#1. 현재 시간의 값 밀리세컨드 변환
        long timestamp = LocalDateTime.now().atZone(zoneid).toInstant().toEpochMilli();
        
        return timestamp;
		
	}
	
	// TimeStamp to Bit
	private static String timeStampToBit(long timestamp) {
		
		//변환
		String timeBinary = Long.toBinaryString(timestamp);
		
		//Console 출력
		System.out.println("현재 시각 milliSeconds는 다음과 같습니다: " + timestamp);
		System.out.println("현재 시각(milliseconds)를 Binary로 바꾸면 다음과 같습니다: " + timeBinary);
		System.out.println("Binary의 길이는 다음과 같습니다: " + timeBinary.length());
        
        return timeBinary;
	}
	
	
	// Bit to TimeStamp
	public static long bitToTimeStamp(String binaryTime) {
		
		// Binary로 되어 있던걸 다시 timestamp로 변환
		long timestamp = Long.parseLong(binaryTime, 2); 
        
        System.out.println("Binary를 역변환하면 다음과 같습니다: " + timestamp);
        
        return timestamp;
		
	}
	
	
	// And Operator &
	
	
	// Or Operator |
	
	
	// XOR Operator ^
	
	
	// Not Operator ~
	
	
	// Shift Operator << >>
	
}
