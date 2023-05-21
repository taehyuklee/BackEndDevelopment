package bulletinBoard.notice.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class NoticeDto {

    private String id;
    private String title;
    private LocalDateTime stTime;
    private LocalDateTime endTime;
    private String type;
    private List<ImgFileDto> imgFile;
    private String cretId;
    private LocalDateTime cretDt;
    private String content;
    
}
