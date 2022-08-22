package com.swagger.swaggerConfig.domain.dto;

import javax.websocket.Encoder.Binary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

import lombok.experimental.Accessors;

@Data
@Document
@Accessors(chain = true)
public class AdmApi {

    @Id
    private String id;
    private String apiId;
    private String sysId;
    private SwaggerSpec swaggerSpec;
    
}
