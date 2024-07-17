package com.web.crawler.cralwer.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
@Accessors(chain=true)
@ToString
public class CollectedUrl {

    @Id
    private String id;

    private String url;

    private boolean collected;

    @CreatedBy
    private String cretId;
    @CreatedDate
    private LocalDateTime cretDt;
    @LastModifiedBy
    private String updId;
    @LastModifiedDate
    private LocalDateTime updDt;

}
