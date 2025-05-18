package com.taehyuk._auth.auth_core.member.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
// PACKAGE - Private & Package
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB에게 맡긴다. (AUTO
    private Long id;

//    @JsonProperty(acess = READ_WRITE)
    @Column(nullable = false, unique=true)
    private  String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;


}
