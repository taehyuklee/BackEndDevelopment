package com.swagger.swaggerConfig.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
// @NoArgsConstructor
// @AllArgsConstructor
@Accessors(chain = true)
public class AdmApiDto {
    
    private String id;
    private String apiId;
    private String sysId;
    private String swaggerSpec;
}
