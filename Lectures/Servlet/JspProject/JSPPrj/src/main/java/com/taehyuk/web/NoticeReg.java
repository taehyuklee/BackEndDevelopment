package com.taehyuk.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice-reg")
public class NoticeReg extends HttpServlet{
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		//Header에도 어떤 형식으로 인코딩했는지 알려줘야 Browser가 그 Header를 보고 해석할수 있다
		response.setContentType("text/html; charset=UTF-8"); //뭐가 됐던 text/html 형식이고 encoding은 UTF-8로 되어 있다를 알려주는 부분이다.
		
				
		PrintWriter out = response.getWriter();
		
		//Request부분의 한글을 바꿔준다
		request.setCharacterEncoding("UTF-8");
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		out.println(title);
		out.println(content);

	}
}
