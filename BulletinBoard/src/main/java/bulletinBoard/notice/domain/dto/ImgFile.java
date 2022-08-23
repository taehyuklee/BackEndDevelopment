package bulletinBoard.notice.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class ImgFile {

    private String originalFileName;
    private byte[] bytes;
    private String contentType;
    private Long size;
    private String fileName;
    //private Resource resource; 
}
