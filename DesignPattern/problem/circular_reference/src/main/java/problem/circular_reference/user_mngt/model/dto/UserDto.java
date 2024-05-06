package problem.circular_reference.user_mngt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String Id;

    private String userNm;

    private String userPassword;

    private String userAuth;

}
