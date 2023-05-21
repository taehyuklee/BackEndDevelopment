package bulletinBoard.notice.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NoticeQueryParameter {

    private String title;
    private String content;
    
}
