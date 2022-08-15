package com.mongoDB.domain.entity;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
//@Document
@Accessors(chain = true)
public class ReqHndlr {

    @DBRef
    private AdmHndlrGrp hndlrGrp;

    @DBRef
    private List<AdmHndlr> hndlr;
    
}
