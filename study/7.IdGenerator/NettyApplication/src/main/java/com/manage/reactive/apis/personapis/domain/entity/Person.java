package com.manage.reactive.apis.personapis.domain.entity;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class Person extends AuditEntity {

    @Transient
    private boolean isNew;

    @Id
    private String id;

    private String email;

    private String name;

    private String phoneNum;

    private Integer score;
    
}
