package problem.circular_reference.usergrp_mngt.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGrpEntity {

    private String Id;

    private String userGrpNm;

    private String grpCd;

    private String deleteYn;

    private String crtId;

    private LocalDateTime crtDt;

    private String updId;

    private LocalDateTime updDt;


}
