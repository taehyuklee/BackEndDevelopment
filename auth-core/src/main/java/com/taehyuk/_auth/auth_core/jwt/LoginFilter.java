package com.taehyuk._auth.auth_core.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taehyuk._auth.auth_core.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException {

        // 클라이언트 요청에서 username, password 추출
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);

        try {
            ObjectMapper om = new ObjectMapper();
            Map<String, String> loginData = om.readValue(request.getInputStream(), Map.class);

            String username = loginData.get("username");
            String password = loginData.get("password");

            System.out.println(username);
            System.out.println(password);

            // username, password, role
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            System.out.println(authToken);

            // token에 담은 검증을 위한 AuthenticationManager에게 던져준다. AuthenticationManager가 검증을 진행할거다.
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse, FilterChain chain, Authentication authentication) {

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException failed) {

    }

    }
