package com.swagger.swaggerConfig.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Info {

    private String description;
    private String version;
    private String title;
    private String termsOfService;
    private Contact contact;
    private License license;
    
}
