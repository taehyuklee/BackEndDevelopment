package com.taehyuk.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/taehyuk")
public class Taehyuk extends HttpServlet{
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		//Header에도 어떤 형식으로 인코딩했는지 알려줘야 Browser가 그 Header를 보고 해석할수 있다
		response.setContentType("text/html; charset=UTF-8"); //뭐가 됐던 text/html 형식이고 encoding은 UTF-8로 되어 있다를 알려주는 부분이다.
		
				
		PrintWriter out = response.getWriter();
		
		
		//앞에 기본적으로 String으로 받아오고 만약 아니면 null로 받아와 질것.
		String cnt_ = request.getParameter("cnt");
		int cnt=0;
		
		if(cnt_ !=null && !cnt_.equals("")) {
			cnt = Integer.parseInt(request.getParameter("cnt"));
		}
		
		
		for(int i=0; i<cnt; i++){
			out.println((i+1) + "안녕 :Hello!!<br>");
		}
	}
}
