package com.mongoDB.domain.entity;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongoDB.domain.dto.AdmApiDto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Document
@Accessors(chain = true)
public class AdmApi extends CommonEntity{

    @Id
    private String id;
    private String apiId;
    private String sysId;
    private String apiNm;
    private String ifNo;
    
    @DBRef
    private AdmHndlr admHndlr;
    private List<Spec> spec;

    // public static AdmApi to(AdmApiDto admApiDto) {
    //     ModelMapper modelMapper = new ModelMapper();

    //     AdmApi admApi = modelMapper.map(admApiDto, AdmApi.class);
    //     return admApi;
    // }

}
