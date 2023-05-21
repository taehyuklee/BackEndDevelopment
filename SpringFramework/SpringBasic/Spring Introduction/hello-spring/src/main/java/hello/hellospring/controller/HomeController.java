package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // -> /는 뭐냐? local host 8080들어오면 바로 호출이 된다.
    //https://coding-nyan.tistory.com/71
    public String home(){
        return "home";
    }
}
