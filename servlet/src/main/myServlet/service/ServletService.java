package com.http.servlet.service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Service;

@WebServlet("/hello")
public class ServletService {

    public void method1(){
        Interface1.print();
    }

    Interface1 interface1 = new Interface1(){
        
    };
    


    
}


interface Interface1 {



    static void print() {
        System.out.print("Hello");
    }
}
