package com.swagger.swaggerConfig.domain.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
