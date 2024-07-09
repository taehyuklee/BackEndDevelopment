package com.ppt.converter;

import com.ppt.converter.makeslides.MakePPT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConverterApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ConverterApplication.class, args);

		MakePPT makePPT = new MakePPT();
		makePPT.makePptx();

	}

}
