package com.config.querydsl.service1.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class MyEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String svcNm;

    private String notice;


}
