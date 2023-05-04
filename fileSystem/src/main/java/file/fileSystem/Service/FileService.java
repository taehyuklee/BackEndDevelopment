package file.fileSystem.Service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class FileService {
    

    public void saveFile(){

        File file = new File("");
		String str = "Hello world!";

		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(str);
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
        
    }


    public void deleteFile(){

        File file = new File("");

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

    public void restTemplate(){
        
    }

}
