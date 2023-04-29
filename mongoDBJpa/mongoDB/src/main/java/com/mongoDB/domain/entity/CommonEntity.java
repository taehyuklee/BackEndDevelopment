package com.mongoDB.domain.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class CommonEntity {

    @CreatedDate
    private LocalDateTime cretDt;
    @LastModifiedDate
    private LocalDateTime updDt;
    @CreatedBy
    private String cretId;
    @LastModifiedBy
    private String updId;
    @Version
    private Long version;

}
