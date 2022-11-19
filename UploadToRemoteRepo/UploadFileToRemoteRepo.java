import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class UploadFileToRemoteRepo {
  
  
	public static void main(String[] args) throws IOException {
		String url = "http:/repoUrl/test2.json";
		String json = "{\"aaa\":\"bbb\"}";
		HttpURLConnection con = null;
    
		con = (HttpURLConnection) new URL(url).openConnection();
		con.setConnectTimeout(3000);
		con.setRequestMethod("PUT");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.addRequestProperty("Authorization", "Basic YWRtaW46bmV3MTIzNCE=");
    
		OutputStream out_stream = con.getOutputStream();
    
		out_stream.write(json.getBytes("UTF-8"));
		out_stream.flush();
		out_stream.close();
    
		System.out.println(con.getResponseCode());
		System.out.println(con.getResponseMessage());
    
    
	}
}
