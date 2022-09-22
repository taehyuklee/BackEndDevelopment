package com.swagger.swaggerConfig.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swagger.swaggerConfig.domain.dto.AdmApi;
import com.swagger.swaggerConfig.domain.dto.AdmApiDto;
import com.swagger.swaggerConfig.service.SwaggerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor //RequiredArgsConstructor -> 필수 생성자들에 한해서 final같은 것들
@Slf4j
public class SwaggerController {

    //@Autowired
    private final SwaggerService swaggerService;

    //Parsing해주는 역할을 해준다.
    @GetMapping("/")
    public String swaggerParse() throws IOException, ParseException{
        return swaggerService.getJson();
    }

    //json파일을 받아서 MongoDB에 저장해주는 것
    @PostMapping(value ="/regSwagger", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void regSwaggerJson(@RequestPart("admApi") AdmApiDto admApi, @RequestPart("jsonFile") MultipartFile swaggerSpec) throws IOException{
        System.out.println("들어오긴 하나?");
        swaggerService.regSwaggerJson(admApi, swaggerSpec);
        log.info("MongoDB에 swaggerSpec이 저장되었습니다.");
    }

    //json파일을 받아서 Front-End에 넘겨주는 것
    @GetMapping("/getSwaggerFile")
    public AdmApiDto getSwaggerConfigFile(@RequestParam("apiId") String apiId, @RequestParam("sysId") String sysId){
        return swaggerService.getSwaggerConfigFile(apiId, sysId);
    }

    //json파일을 받아서 String으로 바꿔서 넘겨주는 것
    @GetMapping("/getSwaggerString")
    public AdmApiDto getSwaggerConfigString(@RequestParam("apiId") String apiId, @RequestParam("sysId") String sysId){
        return swaggerService.getSwaggerConfigFile(apiId, sysId);
    }

    //파일 upload기능
    @PostMapping(value = "/uploadFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void uploadFile(@RequestPart("jsonFile") MultipartFile swaggerSpec) throws IllegalStateException, IOException{
        System.out.println("Hi");
        swaggerService.uploadFile(swaggerSpec);
    }

    //Swagger규격서 바꾸기 (내부에 있는걸로 바꿔주기)
    @GetMapping(value="/changeSwagger")
    public void changeSwaggerJson() throws IOException, ParseException {
        swaggerService.changeSwaggerJson();
    }
    
    
}

/**
 * 
 * org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'requestMappingHandlerMapping' defined in class path resource [org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration$EnableWebMvcConfiguration.class]: Invocation of init method failed; nested exception is java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'swaggerController' method
com.swagger.swaggerConfig.controller.SwaggerController#getSwaggerConfigFile(String, String)
to {GET [/getFile]}: There is already 'swaggerController' bean method
com.swagger.swaggerConfig.controller.SwaggerController#getSwaggerConfigString(String, String) mapped.
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1804) ~[spring-beans-5.3.22.jar:5.3.22]
 * -> Error Archive (RequestMapping URI가 겹쳤을때 일어나는 문제이다.)
 * 
 */

 /*
 2022-08-20 16:48:06.780 ERROR 16404 --- [nio-9003-exec-5] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.reflect.InaccessibleObjectException: Unable to make jdk.internal.ref.PhantomCleanable() accessible: module java.base does not "opens jdk.internal.ref" to unnamed module @4292f01d] with root cause

java.lang.reflect.InaccessibleObjectException: Unable to make jdk.internal.ref.PhantomCleanable() accessible: module java.base does not "opens jdk.internal.ref" to unnamed module @4292f01d

- MultipartFile에서 MediaType을 설정 안해줘서 나는 에러??
- Entity
 
Multi resolover 안해줘서 그럼 MultipartFile Resolver해줄수 있는거 해야함.
 
 */