package com.taehyuk._auth.auth_core.member.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

    private String username;
    private String password;
    private String grade;
    private String email;
    private String subsidiary;

}
