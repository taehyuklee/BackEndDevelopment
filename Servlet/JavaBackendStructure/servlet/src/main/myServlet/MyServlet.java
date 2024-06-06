package com.http.servlet.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.startup.Tomcat;


@WebServlet("/myServlet")
public class MyServlet extends HttpServlet{

    ServletContext servletContext = getServletContext();
    ServletContext servletCon = new ApplicationContext();
    Tomcat tomcat = new Tomcat();

    public MyServlet(){
        super();
    }

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
    }

    public void destroy(){
        super.destroy();
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String method = req.getMethod();

        if(method.equals("GET")){
            doGet(req,resp);
        } else if(method.equals("HEAD")){
            doHead(req, resp);
        } else if(method.equals("POST")){
            doPost(req,resp);
        } else if(method.equals("PUT")){
            doPut(req, resp);
        } else if(method.equals("DELETE")){
            doDelete(req,resp);
        } else if(method.equals("OPTIONS")){
            doOptions(req, resp);
        } else if(method.equals("TRACE")){
            doTrace(req, resp);
        } else{
            String errMsg = "http";
        }

        super.service(req, resp);
    }
    
}
