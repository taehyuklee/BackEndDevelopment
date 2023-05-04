package com.swagger.swaggerConfig.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swagger.swaggerConfig.domain.dto.AdmApi;
import com.swagger.swaggerConfig.domain.dto.AdmApiDto;
import com.swagger.swaggerConfig.domain.dto.SwaggerDto;
import com.swagger.swaggerConfig.domain.dto.SwaggerSpec;
import com.swagger.swaggerConfig.domain.repository.SwaggerRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SwaggerService {

    private final SwaggerRepository swaggerRepository;

    StringBuilder stringBuilder = new StringBuilder();

    public void changeInfo(){

    }

    public void changePath(){

    }

    public void slash(String string){

        for(int i=0; i<string.length(); i++){
            if(string.charAt(i) == '/'){

            }
        }
    }

    public void changeSwaggerJson() throws IOException, ParseException{
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("/Users/thlee/BackEnd/BackEndDevelopment/SwaggerConfig/SwaggerConfig/sample.json");

        JSONObject jsonObject = (JSONObject) parser.parse(reader); //object로 만들었다.
        Map<String, Object> map1 = (HashMap<String, Object>) jsonObject;
        System.out.println(map1);

        System.out.println("//////////");

        map1.replace("host", jsonObject.get("host"), "apiGatewayURL Write Something");
        map1.replace("basePath", jsonObject.get("basePath"), "/apiGatewayV");

        System.out.println(map1.get("host"));
        System.out.println(map1.get("basePath"));

        SwaggerDto swaggerDto = new SwaggerDto();

        // System.out.println(jsonObject.toJSONString());
        // System.out.println(jsonObject.entrySet());

        //Entry<Object, Object> = jsonObject.entrySet();
        jsonObject.replace("host", jsonObject.get("host"), "apiGatewayURL");
        jsonObject.replace("basePath", jsonObject.get("basePath"), "/apiGatewayV");

        System.out.println();
        for(Object key: jsonObject.keySet()){
            Map<String, Object> map = new HashMap<String, Object>();
            Object value = jsonObject.get(key);
            //System.out.println(value);
            //System.out.println();

            map.put((String) key, value);
            //System.out.println(map);

        }

        System.out.println(jsonObject.get("host"));
        System.out.println(jsonObject.get("basePath"));
    
    }

    public Map<String, Object> recursiveOb(){

        return new HashMap<String, Object>();
    }



    //Object Mapper가 잘 작동하는지 확인하기 위함
    public String getJson() throws IOException, ParseException{

        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("/Users/thlee/BackEnd/BackEndDevelopment/SwaggerConfig/SwaggerConfig/sample.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        String targetString = jsonObject.toString();
        JSONObject targetObject = (JSONObject) parser.parse(targetString);

        // System.out.println(targetString);
        System.out.println(targetObject.toString());


        if(targetObject.equals(jsonObject)){
            System.out.println("True");
        }else{
            System.out.println("False");
        }

        SwaggerDto swaggerDto = new SwaggerDto();

        swaggerDto.setBasePath((String) jsonObject.get("basePath")).setSecurityDefinitions(jsonObject.get("securityDefinitions"))
                .setSchemes(jsonObject.get("schemes"));

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

        admApiEntity.setApiId(admApiDto.getApiId()).setSwaggerSpec(swaggerSpecObject).setSysId(admApiDto.getSysId());
        swaggerRepository.save(admApiEntity);
    }



    //시나리오 Swagger json 받아오고 mongodb에서 받아오고 그거 그대로
    public AdmApiDto getSwaggerConfigFile(String apiId, String sysId){
        //apiId랑 systemId로 파일을 검색할수 있게 하거나, objectId로 검색할수 있게 하거나 둘 중 하나
        //MongoDB 다른 Collection에서는 당연히 

        AdmApi entity = swaggerRepository.findByApiIdAndSysId(apiId, sysId);

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

    
    //다운로드할때
	public void download(HttpServletResponse response) {
        // 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
		String fileName = "picture.jpg";
		String saveFileName = "c:/tmp/picture.jpg";
		String contentType = "application/json";
                File file = new File(saveFileName);
                long fileLength = file.length();
                //파일의 크기와 같지 않을 경우 프로그램이 멈추지 않고 계속 실행되거나, 잘못된 정보가 다운로드 될 수 있다.

                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
                response.setHeader("Content-Transfer-Encoding", "binary");
                response.setHeader("Content-Type", contentType);
                response.setHeader("Content-Length", "" + fileLength);
                response.setHeader("Pragma", "no-cache;");
                response.setHeader("Expires", "-1;");

                try(

                        FileInputStream fis = new FileInputStream(saveFileName);
                        OutputStream out = response.getOutputStream();
                ){
                        int readCount = 0;
                        byte[] buffer = new byte[1024];
                    while((readCount = fis.read(buffer)) != -1){
                            out.write(buffer,0,readCount);
                    }
                }catch(Exception ex){
                    throw new RuntimeException("file Save Error");
                }
	}

    public void convertSwagger(){

        ObjectMapper mapper = new ObjectMapper();


        
    }

    
}
