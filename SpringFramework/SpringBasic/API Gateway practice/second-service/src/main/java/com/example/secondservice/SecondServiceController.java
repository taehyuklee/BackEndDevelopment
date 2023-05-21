package com.example.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //화면을 갖고있지 않고 RestController 역할을 해줄것이다.
@RequestMapping("/second-service") //사용자로부터 요청되는 URI값을 지정해두는 것.
@Slf4j
public class SecondServiceController {
//일반적인 Controller와 RestController의 차이는 request Body와 response body를 구현하느냐 제공되는 것을 쓰느냐 차이점이다.
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the Second service";
    }


    @GetMapping("/message") //first-request header를 받는다.
    public String message(@RequestHeader("second-request") String header){
        //@RequestHeader header값을 받아 갈 것이다.
        log.info(header);
        return "Hello world in Second Service.";
    }

}
