package com.taehyuk._auth.auth_core.config.security_service;

import com.taehyuk._auth.auth_core.member.domain.entity.Member;
import com.taehyuk._auth.auth_core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("let's check it out");
        try {
            System.out.println(username);
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 이게 뭔지 한 번 확인하기.
            return new User(member.getUsername(),
                    member.getPassword(),
                    member.getRole() != null ? List.of(new SimpleGrantedAuthority(member.getRole())) : List.of());

        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
