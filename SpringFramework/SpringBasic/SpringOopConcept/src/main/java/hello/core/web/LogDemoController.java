package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //private final ObjectProvider<MyLogger> myLoggerProvider; //주입시점을 뒤로 미룰수 있다.
    private final MyLogger myLogger;

    @SneakyThrows
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();
        //MyLogger myLogger = myLoggerProvider.getObject(); // 내가 필요한 시점에 이렇게 불러올수 있다

        System.out.println("myLogger = " + myLogger.getClass()); //myLogger = class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$5ee69f27
        myLogger.setRequestURL(requestURL); //[8f43771f-c83a-4f3e-9a13-0c7ae9c84a54][ request scope bean create:hello.core.common.MyLogger@19d030c4

        //Thread.sleep(1000);

        myLogger.log("controller test"); //[8f43771f-c83a-4f3e-9a13-0c7ae9c84a54][http://localhost:8080/log-demo][ controller test
        logDemoService.logic("testId"); //[8f43771f-c83a-4f3e-9a13-0c7ae9c84a54][http://localhost:8080/log-demo][ service id =testId

        return "OK";
        //끝날때
        //[114922bd-4b36-4bc7-b242-6e56db4b3b73][ request scope bean close:hello.core.common.MyLogger@1e814a52
    }
}
