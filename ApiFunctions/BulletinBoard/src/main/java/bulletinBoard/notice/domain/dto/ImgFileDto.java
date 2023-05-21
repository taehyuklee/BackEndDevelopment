package bulletinBoard.notice.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class ImgFileDto {

    private String originalFileName;
    private String contentType;
    private String imgBytes;
    private Long size;
    private String fileName;
    private String extension;
    
}
