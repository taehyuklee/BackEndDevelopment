package com.calculator.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calc")
public class Calc extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Encoding decoding과 형식을 모두 UTF-8로 통일한다.
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//input form에서 name="operator"의 value값을 보고 "덧셈", "뺄셈"을 구분할수 있다. 
		String op = request.getParameter("operator");
		
		/*
		 * <input type="submit" name="operator" value="덧셈" />
		 * 여기서 value의 key를 operator로 넘겨주게 되는 형식이다 즉,input button의 정보를 넘겨주게 됨. \
		 * 이게 없으면 x, y값만 넘어옴
		 * */
		
		//들어오는 건 무조건 문자열로 받아온다고 함. 
		String x_ = request.getParameter("x");
		String y_ = request.getParameter("y");

		
		int x = 0;
		int y = 0;
		
		if(!x_.equals("")) x = Integer.parseInt(x_);
		if(!y_.equals("")) y = Integer.parseInt(y_);
		
		int result = 0;
		
		if(op.equals("덧셈"))
			result = x+y;
		else
			result = x-y;
		
		response.getWriter().printf("result is %d\n", result);

	}

}
