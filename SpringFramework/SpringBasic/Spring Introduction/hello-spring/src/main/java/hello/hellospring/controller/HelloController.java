package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") //Web application에서 /hello라고 들어오면 이 method를 호출해준다.
    //Get방식의 get이다.
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        //MVC에서의 model을 의미한다
        return "hello";
        //return hello --> resources의 hello.html로 가서 rendering하라는거다.
        //무엇을 rendering하느냐? data 키값에 hello!!를 넣어서 가져간다.
        //resources:templates/ +{ViewName}+ .html
    }
    
    @GetMapping("hello-mvc") //Parameter를 받아온다
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string") //Parameter를 받아온다
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name; // 문자 그대로를 넘겨준다
    }

    //데이터를 내놓으라고 할때
    @GetMapping("hello-api") //Parameter를 받아온다
    @ResponseBody
    public Hello helloAPi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //객체를 return하면 어떻게 돌까?
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
