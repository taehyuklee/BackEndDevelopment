package design.pattern.proxy.app.version1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController //RequestMapping만 넣어도 Controller인식이 가능하다고 했었는데, 여기서는 안돼서 RestController를 붙이고 실행함.
@RequestMapping //스프링은 @Controller 또는 @RequestMapping이 있어야 스프링 컨트롤러로 인식할수 있다.
@ResponseBody
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId); //interface에는 꼭 RequestParam이름을 명시적으로 적시한다 (Compile때 문제가 생길수 있음)

    @GetMapping("/v1/no-log")
    String noLog();

    
}
