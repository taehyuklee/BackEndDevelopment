package download.excel.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class ListEntity {

    @Id
    private String id;
    private int humidity;
    private int gorani;
    private Boolean rain;
    private String gampangE;
    
    
}
