package com.swagger.swaggerConfig.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.swagger.swaggerConfig.domain.dto.AdmApi;
import com.swagger.swaggerConfig.domain.dto.AdmApiDto;
import com.swagger.swaggerConfig.domain.dto.SwaggerDto;
import com.swagger.swaggerConfig.domain.dto.SwaggerSpec;
import com.swagger.swaggerConfig.domain.repository.SwaggerRepository;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SwaggerService {

    private final SwaggerRepository swaggerRepository;

    public String getJson() throws IOException, ParseException{

        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("C:\\Users\\thlee\\Desktop\\BackEnd\\swaggerConfig\\src\\main\\json\\sample.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        // System.out.println(jsonObject.toString());
        // System.out.println("////////////////////");

        // System.out.println(jsonObject.get("swagger"));

        String targetString = jsonObject.toString();
        JSONObject targetObject = (JSONObject) parser.parse(targetString);

        // System.out.println(targetString);
        // System.out.println(targetObject.toString());

        if(targetObject.equals(jsonObject)){
            System.out.println("True");
        }else{
            System.out.println("False");
        }

        SwaggerDto swaggerDto = new SwaggerDto();

        //System.out.println(jsonObject.get("paths"));

        swaggerDto.setBasePath((String) jsonObject.get("basePath")).setSecurityDefinitions(jsonObject.get("securityDefinitions"))
                .setSchemes(jsonObject.get("schemes"));

        // System.out.println("////////////////////");
        // System.out.println(swaggerDto.getSecurityDefinitions().toString());

        return jsonObject.toString();
    }

    public void convert(){
        //나중에 혹시 뭔가 변환되어서 swagger내부적인 설정이 바뀌어야 할 부분을 method를 만들어 줘야 한다.
    }




    public void uploadFile(MultipartFile file) throws IllegalStateException, IOException{

        //MultiPartFile을 Java의 File객체로 바꿀수 있다.
        File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);

        System.out.println(convFile.getPath());
        

    }



    public void regSwaggerJson(AdmApiDto admApiDto, MultipartFile swaggerSpec) throws IOException{

        AdmApi admApiEntity = new AdmApi();
        SwaggerSpec swaggerSpecObject = new SwaggerSpec();
        swaggerSpecObject.setContentType(swaggerSpec.getContentType())
                        .setFileName(swaggerSpec.getName())
                        .setSize(swaggerSpec.getSize())
                        .setOriginalFileName(swaggerSpec.getOriginalFilename())
                        .setBytes(swaggerSpec.getBytes())
                        .setResource(swaggerSpec.getResource());
        
                        //String  originalFileExtension = originalFile.substring(originalFile.lastIndexOf(".")); 확장자명
        // System.out.println("URI--------------------------");
        // //System.out.println(swaggerSpec.getResource().getURI());
                        
        // System.out.println("URL--------------------------");
        // System.out.println(swaggerSpec.getResource().getURL());

        // System.out.println("File ------------------------");
        // File file = swaggerSpec.getResource().getFile();
        // System.out.println(swaggerSpec.getResource().getFile());

        // System.out.println("Description-------------------------");
        // System.out.println(swaggerSpec.getResource().getDescription()); 

        // System.out.println("InputStream-------------------------");
        // System.out.println(swaggerSpec.getResource().getInputStream()); 

        admApiEntity.setApiId(admApiDto.getApiId()).setSwaggerSpec(swaggerSpecObject).setSysId(admApiDto.getSysId());
        swaggerRepository.save(admApiEntity);
    }



    //시나리오 Swagger json 받아오고 mongodb에서 받아오고 그거 그대로
    public AdmApiDto getSwaggerConfigFile(String apiId, String sysId){
        //apiId랑 systemId로 파일을 검색할수 있게 하거나, objectId로 검색할수 있게 하거나 둘 중 하나
        //MongoDB 다른 Collection에서는 당연히 

        System.out.println("/////////////////////////111");

        AdmApi entity = swaggerRepository.findByApiIdAndSysId(apiId, sysId);
        
        

        System.out.println("/////////////////////////222");

        // String swaggerString = Base64.getEncoder().encodeToString(entity.getSwaggerSpec());
        // byte[] hi = Base64.getEncoder().encode(entity.getSwaggerSpec());
        // String b  = Base64.getDecoder().decode(hi).toString();

        String coverted = new String(entity.getSwaggerSpec().getBytes(), StandardCharsets.UTF_8);

        System.out.println(coverted);
        //String swaggerString = Base64.getDecoder().decode(entity.getSwaggerSpec()).toString();

        AdmApiDto admApiDto = new AdmApiDto().setApiId(entity.getApiId()).setSysId(entity.getSysId())
                                .setSwaggerSpec(coverted).setId(entity.getId());
        
        //MultipartFile swaggerSpecEntity = entity.getSwaggerSpec();
        //String fileName = swaggerSpecEntity.getOriginalFilename();

        //log.info(admApiDto.toString());

        return admApiDto;
    }

    
}
