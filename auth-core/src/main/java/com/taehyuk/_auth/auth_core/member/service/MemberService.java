package com.taehyuk._auth.auth_core.member.service;

import com.taehyuk._auth.auth_core.member.domain.dto.MemberDto;
import com.taehyuk._auth.auth_core.member.domain.entity.Member;
import com.taehyuk._auth.auth_core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerProcess(MemberDto memberDto) {
        String username = memberDto.getUsername();
        String password = memberDto.getPassword();

        if(memberRepository.existsByUsername(username)){
            return;
        }

        Member member = new Member();

        member.setUsername(username);
        member.setPassword(bCryptPasswordEncoder.encode(password));
        member.setRole("ROLE_ADMIN"); // Spring : 접두사_ 권한
        member.setEmail("email");

        memberRepository.save(member);
    }

    public Map<String, String> getInformation() {
        return Map.of(
            "data", "비밀이 아니야"
        );
    }

    public Map<String, String> getInfoForSuper() {
        return Map.of(
                "data", "for Super User"
        );
    }

    public Map<String, String> getInfoForSpecial() {
        return Map.of(
                "data", "for Special User"
        );
    }

}
