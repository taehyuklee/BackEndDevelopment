package header.httpUrlConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HttpUrlConnectionApplication {

	public static void main(String[] args){
		SpringApplication.run(HttpUrlConnectionApplication.class, args);

		// URL url = new URL("http://platform-digico.com:8080/mngt/v1/user/getUserAuthById?userId=beast%40kt.com");
		// URLConnection urlCon = url.openConnection();
		// HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		
		// Map<String, List<String>> info = httpCon.getHeaderFields();

		// for(Entry<String, List<String>> entry : info.entrySet()){

		// 	System.out.println(entry.getKey() + " " + entry.getValue() + " ");

		// }

		// //실제 데이터 읽기
		// InputStream inputStream = urlCon.getInputStream();
		// //InputStreamReader reader = new InputStreamReader(inputStream);

		// BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		// String line = reader.readLine(); // reads a line
		// System.out.println(line);

		try {
			start();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static void start() throws IOException{
		URL url;

		url = new URL("http://platform-digico.com:8080/mngt/v1/user/getUserAuthById?userId=beast%40kt.com");

		URLConnection urlCon = url.openConnection();
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		
		Map<String, List<String>> info = httpCon.getHeaderFields();

		for(Entry<String, List<String>> entry : info.entrySet()){

			System.out.println(entry.getKey() + " " + entry.getValue() + " ");

		}

		//실제 데이터 읽기
		InputStream inputStream = urlCon.getInputStream();
		//InputStreamReader reader = new InputStreamReader(inputStream);

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = reader.readLine(); // reads a line
		System.out.println(line);


		//RestTemplate으로 시도하기s
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders header = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<>(header);

		restTemplate.exchange("http://platform-digico.com:8080/mngt/v1/user/getUserAuthById?userId=beast%40kt.com", HttpMethod.GET, entity, Map.class);
		

	}


	@RestController
	public static class GetConrollerPractice{

		@RequestMapping("/")
		public void geHeaderInfo(@RequestHeader HttpHeaders headers){
			
			System.out.println(headers.toSingleValueMap().toString());
		}
		
	}

}
