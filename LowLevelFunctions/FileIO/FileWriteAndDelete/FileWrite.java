import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {
	
	public static void main(String[] args) throws InterruptedException {
		
		File file = new File("C:\\Java\\text.txt");
		String str = "Hello world!";

		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(str);
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Thread.sleep(10000);
		//RestTemplate 넣으면 된다.
		
		
		
    	if( file.exists() ){
    		if(file.delete()){
    			System.out.println("파일삭제 성공");
    		}else{
    			System.out.println("파일삭제 실패");
    		}
    	}else{
    		System.out.println("파일이 존재하지 않습니다.");
    	}
        	
	}
	
	
}
