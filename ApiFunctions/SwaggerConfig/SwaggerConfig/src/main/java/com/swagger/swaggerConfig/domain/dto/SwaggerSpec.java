package com.swagger.swaggerConfig.domain.dto;

import org.springframework.core.io.Resource;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class SwaggerSpec {

    private String originalFileName;
    private byte[] bytes;
    private String contentType;
    private Long size;
    private String fileName;
    private Resource resource; 

}
