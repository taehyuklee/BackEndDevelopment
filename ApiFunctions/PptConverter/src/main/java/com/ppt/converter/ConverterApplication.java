package com.ppt.converter;

import com.ppt.converter.makeslides.MakePPT;
import com.ppt.converter.makeslides.functions.PutImage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConverterApplication {

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(ConverterApplication.class, args);

		// 임의의 데이터로 테이블 채우기 (예시 데이터)
		String[][] data = {
				{"이름", "나이", "직업", "성별"},
				{"홍길동", "30", "개발자", "남"},
				{"김철수", "25", "디자이너"},
				{"박영희", "28", "마케터", "여"}
		};

		MakePPT makePPT = new MakePPT();
		makePPT.makePptx(data);

	}

}
