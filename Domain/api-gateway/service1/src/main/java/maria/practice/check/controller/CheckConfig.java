package maria.practice.check.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @GetMapping("/getConfig")
    public String getUrl(){
        return url;
    }
}
