package bulletinBoard.notice.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Document
@Accessors(chain=true)
public class NoticeMultipartFile {

    @Id
    private String id;
    private String title;
    private LocalDateTime stTime;
    private LocalDateTime endTime;
    private String type;
    private List<MultipartFile> imgFile;
    private String content;
    @CreatedBy
    private String cretId;
    @CreatedDate
    private LocalDateTime cretDt;
    @LastModifiedBy
    private String updId;
    @LastModifiedDate
    private LocalDateTime updDt;
    
}
