package com.mongoDB.domain.dto;

import lombok.Data;

@Data
public class AdmHndlrDto {
    
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
}
