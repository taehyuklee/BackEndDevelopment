package com.calculator.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add2")
public class ArrayAdd extends HttpServlet {
	
	//여기서는 html에서 받아온 것들을 배열로 받아오게 될 것이다

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Encoding decoding과 형식을 모두 UTF-8로 통일한다.
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//들어오는 건 무조건 문자열로 받아온다고 함. 
		String[] num_ = request.getParameterValues("num");

		
		int result = 0;

		for(int i=0; i<num_.length; i++) {
			int num = Integer.parseInt(num_[i]);
			result += num;
		}
		
		response.getWriter().printf("result is %d\n", result);

	}

}
