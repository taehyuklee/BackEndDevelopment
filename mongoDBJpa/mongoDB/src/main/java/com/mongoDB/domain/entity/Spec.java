package com.mongoDB.domain.entity;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Spec extends CommonEntity{

    @Size(max = 4)
    private String ver;
    @Size(max = 50)
    private List<String> meth;
    @Size(max = 50)
    private String in;
    @Size(max = 50)
    private String out;

    @DBRef
    private AdmHndlrGrp reqHndlrGrp;

    @DBRef
    private AdmHndlrGrp resHndlrGrp;

    private ReqHndlr reqHndlr;

    private ResHndlr resHndlr;

    private AdmHndlr errHndlr;

    private String swaggerSpec;
    
    private long timeOut;
    @Size(max = 1000)
    private String desc;
    @Size(max = 20)
    private String sttus;
    private Boolean dply;
}
