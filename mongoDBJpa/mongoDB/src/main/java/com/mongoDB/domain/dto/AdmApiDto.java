package com.mongoDB.domain.dto;

import java.util.List;

import com.mongoDB.domain.entity.AdmHndlr;
import com.mongoDB.domain.entity.Spec;

import lombok.Data;

@Data
public class AdmApiDto {

    private String id;
    private String apiId;
    private String sysId;
    private String apiNm;
    private String ifNo;
    private List<Spec> spec;
    private AdmHndlr admHndlr;
    
}
