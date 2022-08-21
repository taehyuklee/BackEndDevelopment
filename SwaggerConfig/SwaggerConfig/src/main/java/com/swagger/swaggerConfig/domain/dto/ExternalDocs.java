package com.swagger.swaggerConfig.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalDocs {

    private String description;
    private String url;
    
}
