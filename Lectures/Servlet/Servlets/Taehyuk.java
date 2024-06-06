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
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
		
		//Header에도 어떤 형식으로 인코딩했는지 알려줘야 Browser가 그 Header를 보고 해석할수 있다
		resp.setContentType("text/html; charset=UTF-8"); //뭐가 됐던 text/html 형식이고 encoding은 UTF-8로 되어 있다를 알려주는 부분이다.
		
		
		PrintWriter out = resp.getWriter();
		
		
		for(int i=0; i<100; i++){
			out.println((i+1) + "안녕 :Hello!!<br>");
		}
	}
}
