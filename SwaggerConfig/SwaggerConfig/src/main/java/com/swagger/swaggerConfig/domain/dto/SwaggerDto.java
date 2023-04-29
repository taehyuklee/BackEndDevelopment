package com.swagger.swaggerConfig.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class SwaggerDto {
    
    private String swagger;
    private Info info;
    private String host;
    private String basePath;
    private Object tags;
    private Object schemes;
    private Paths paths;
    private Object securityDefinitions;
    private Object definitions;
    private Object externalDocs;
    
}
