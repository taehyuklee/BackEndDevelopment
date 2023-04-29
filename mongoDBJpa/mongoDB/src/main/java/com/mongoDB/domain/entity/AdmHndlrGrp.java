package com.mongoDB.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongoDB.domain.dto.AdmHndlrGrpDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class AdmHndlrGrp extends CommonEntity {

    @Id
    private String id;
    private String hndlrGrpId;
    private String trtSect;
    @DBRef
    private List<AdmHndlr> hndlr;
    private String desc;


    // public static AdmHndlrGrp to(AdmHndlrGrpDto admHndlrGrpDto) {
    //     ModelMapper modelMapper = new ModelMapper();

    //     modelMapper.typeMap(AdmHndlrGrpDto.class, AdmHndlrGrp.class).addMappings(mapper -> {
    //         mapper.map(AdmHndlrGrpDto::getHndlr, AdmHndlrGrp::setHndlr);
    //     });

    //     AdmHndlrGrp admHndlrGrp = modelMapper.map(admHndlrGrpDto, AdmHndlrGrp.class);
    //     return admHndlrGrp;
    // }

}
