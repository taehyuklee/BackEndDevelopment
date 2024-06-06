package com.calculator.web.container;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/application-calc")
public class ApplicationCalculator extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//이 부분이 핵심 (이전 서블릿에 서블릿이 더하거나 뺀것에 대해 기억하고 있는 것을 빼오는 부분이다)
		ServletContext application = request.getServletContext(); //ServletContext를 가져온다.
		
		//Encoding decoding과 형식을 모두 UTF-8로 통일한다.
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//여기서부터는 진짜 계산기처럼 하나의 숫자씩 더하고 빼고 연산을 하게 될 것이다.
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");

		int v = 0;

		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		//값을 계산
		if(op.equals("=")) {
			
			int x = (Integer) application.getAttribute("value"); //이전에 servlet context에 저장되어 있는 값
			int y = v; //지금 사용자가 넣어준 값
			
			String operator = (String) application.getAttribute("op");
			
			int result = 0;
			
			if(operator.equals("+"))
				result = x+y;
			else
				result = x-y;
			
			response.getWriter().printf("result is %d\n", result);
			
		}else {
			//덧셈 뺄셈이 오면, servletContext에 저장하는 로직으로 하고
			application.setAttribute("value", v); //servlet Context에 값을 저장하게 된다 Map형식처럼
			application.setAttribute("op", op); //operatino도 저장할수 있다.
	
		}

		

	}

}
