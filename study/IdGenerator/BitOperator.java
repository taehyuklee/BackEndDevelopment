import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class BitOperator {
	
	 private static final long TWEPOCH = 1288834974657L;
	

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
        //사용할 zone 아이디 값 입니다.
        ZoneId zoneid = ZoneId.of("Asia/Seoul");

        //#1. 현재 시간의 값 밀리세컨드 변환
        long timestamp = LocalDateTime.now().atZone(zoneid).toInstant().toEpochMilli();
        
        System.out.println(timestamp);
        
        System.out.println(Long.toBinaryString(timestamp));
        System.out.println(Long.toBinaryString(timestamp).length());
        
        System.out.println(Long.toBinaryString(TWEPOCH));
        
        Date timeInDate = new Date(TWEPOCH);
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
	
	// And Operator
	
	
	// Or Operator
	
	
	// XOR Operator
	
	
	// Not Operator
	
	
	// Shift Operator
	
}
