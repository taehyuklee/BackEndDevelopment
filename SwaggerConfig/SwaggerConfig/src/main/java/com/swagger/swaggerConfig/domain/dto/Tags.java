package com.swagger.swaggerConfig.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tags {
    
    private String name;
    private String description;
    private ExternalDocs externalDocs;
    
}
