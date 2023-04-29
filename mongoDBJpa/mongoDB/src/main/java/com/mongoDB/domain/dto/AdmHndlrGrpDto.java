package com.mongoDB.domain.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.mongoDB.domain.entity.AdmHndlr;

import lombok.Data;

@Data
public class AdmHndlrGrpDto {

    private String hndlrGrpId;
    private String trtSect;
    @DBRef
    private List<AdmHndlr> hndlr;
    private String desc;
    
}
