package com.mongoDB.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongoDB.domain.dto.AdmHndlrDto;

@Data
@Document
//@NoArgsConstructor
@EqualsAndHashCode(callSuper=false) 
public class AdmHndlr extends CommonEntity {

    @Id
    private String id;
    private String hndlrId;
    private String trtSect; // REQ/RES/ERR
    private String sttus;
    private String type; // SYS/CSTM
    private long order;
    private boolean bodyRfrn;
    private String cstmClassAdr;
    private String cstmClassSrc;
    private String cstmClassNm;
    private String desc;

    public static AdmHndlr to(AdmHndlrDto admHndlrDto) {
        ModelMapper modelMapper = new ModelMapper();

        AdmHndlr admHndlr = modelMapper.map(admHndlrDto, AdmHndlr.class);
        return admHndlr;
    }
}
