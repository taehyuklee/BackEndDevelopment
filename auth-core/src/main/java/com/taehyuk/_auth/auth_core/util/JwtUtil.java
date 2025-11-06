package com.taehyuk._auth.auth_core.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

//    private final String SECRET_KEY = "your-secret-key";

    @Value("${jwt.secret}")
    private String secretKey;

    private final Key key = Keys.hmacShaKeyFor("your-256-bit-secret-your-256-bit-secret".getBytes());

    @Value("${jwt.expiration-ms:3600000}")
    private long expirationMs; // 기본 1시간


    public String createJwt(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 사용자 이름 추출
     */
    public String getUsername(String token){
//        return Jwts.parser().setSigningKey(key)
//                .parseClaimsJws(token).getBody().getSubject();
        return parseClaims(token).getBody().getSubject();
    }

    /**
     * 토큰 검증 (만료, 위조 체크)
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token); // 예외 발생 안 하면 유효
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("토큰 만료");
        } catch (JwtException e) {
            System.out.println("유효하지 않은 토큰");
        }
        return false;
    }

    /**
     * Claims 파싱
     */
    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
