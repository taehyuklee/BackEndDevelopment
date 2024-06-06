import java.util.*;
import java.io.*;
import java.math.*;

public class FileReadAndCal {
	
	public static void calculator(BufferedReader reader, String con) throws IOException {
		
		
		double avgTime = 0D;
		double totNum = 10001L;
		//Input Output 대기시간은 예외 계산에서 빼줘야함 noControl일때 
		
		for(int i=0; i<totNum; i++) {
			
			if(i ==0) {
				String line = reader.readLine();
				String[] stringLine = line.split(" ");
				System.out.println(stringLine[2]);

			}else {
				
				String line = reader.readLine();
				String[] stringLine = line.split(" ");
				//System.out.println(stringLine[2]);
				
				if(Double.parseDouble(stringLine[2].toString())>1.0D) {
					continue;
				}
				

				avgTime += Double.parseDouble(stringLine[2].toString());
			}
			
		}
		
		avgTime /= totNum;
		
		System.out.println(avgTime);
		
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(
			    new InputStreamReader(new FileInputStream("/Users/thlee/Desktop/RevisionOfPRF/Control"), "UTF-8")
			);
		
		calculator(reader, "Con");
		
		reader = new BufferedReader(
			    new InputStreamReader(new FileInputStream("/Users/thlee/Desktop/RevisionOfPRF/NoControl"), "UTF-8")
			);
		
		calculator(reader, "No");
		
	}

}
