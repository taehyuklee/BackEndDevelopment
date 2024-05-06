package problem.circular_reference.user_mngt.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private String Id;

    private String userNm;

    private String userPassword;

    private String userAuth;

    private String deleteYn;

    private String crtId;

    private LocalDateTime crtDt;

    private String updId;

    private LocalDateTime updDt;

}
