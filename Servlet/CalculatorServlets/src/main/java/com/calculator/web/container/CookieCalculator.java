package com.calculator.web.container;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cookie-calc")
public class CookieCalculator extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies();
		
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
			
			//브라우저는 여러 Cookie들을 가지고 있으므로, 내가 심어준 쿠키를 다음과 같이 찾아줘야 한다.
			int x = 0;
			for(Cookie c : cookies) {
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			}
			
			int y = v; //지금 사용자가 넣어준 값
			
			String operator = "";
			for(Cookie c : cookies) {
				if(c.getName().equals("op")) {
					operator = c.getValue();
					break;
				}
			}
			
			
			int result = 0;

			
			if(operator.equals("+"))
				result = x+y;
			else
				result = x-y;
			
			response.getWriter().printf("result is %d\n", result);
			
		}else {
			Cookie valueCookie = new Cookie("value", String.valueOf(v)); //Cookie는 문자열로만 저장 가능하다
			Cookie opCookie = new Cookie("op", op);
			
			//여기서는 쿠키에 옵션을 줘보도록 하자.
			/*
			 * 서블릿마다 쿠키를 선택할수 있는 옵션이 존재한다. 
			 *	(모든 서블릿마다 쿠키를 모두 들고 다니는건 정말 비효율적이다) 
			 *	→ 해당 서블릿의 URL로 식별이 가능하다. 
			 *	→ 쿠키의 이름 충돌난다던지 등을 방지할수 있다.
			 */
			
			// "/cookie-calc" 경로의 서블릿에 대해서만 client단에서 valueCookie와 opCookie를 보내주게 설정했다.
			valueCookie.setPath("/cookie-calc"); // "/" 해당 valueCookie는 모든 서블릿에서 가져올때마다 가져와라의 의미가 됨. 
			opCookie.setPath("/cookie-calc"); // "/add"라고 하면 해당 서블릿에 전달되지 않을 것이다 Cookie
			
			
			//유효기간 설정
			valueCookie.setMaxAge(24*60*60); //s단위로 된다. (쿠키가 하루동안은 살아있어야 한다 client컴퓨터가 꺼지든 뭐하든간에)
			
			
			//Client에 보내기 위해 Cookie를 response에 넣어준다
			response.addCookie(valueCookie);
			response.addCookie(opCookie);
		}

		

	}

}
