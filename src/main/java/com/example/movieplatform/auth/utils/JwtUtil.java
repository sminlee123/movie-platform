package com.example.movieplatform.auth.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // 개발자가 만드는 시크릿 값
    // 나중에 옮기기
    private static final String SECRET = "7JiB7ZmU7ZSM656r7Y+87Y+s7Yq47Y+066as7Jik7ZSE66Gc7KCd7Yq4";

    // 키 디코딩
    private static final Key KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    // 권한 넣기 (열거형으로 받아서 넣기?)
    public String generateAccessToken(String userEmail, String role) {
        return Jwts.builder()
                .setSubject(userEmail)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 만료 1시간
                .signWith(KEY)
                .compact();
    }

    public String generateRefreshToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일
                .signWith(KEY)
                .compact();
    }

    // 토큰에서 claim 읽기
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().
                setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 검증
    public void validateToken(String token) {
        parseClaims(token);
    }

    // 유저 이메일 반환
    public String getUserEmail(String token) {
        return parseClaims(token).getSubject();
    }

    // 유저 권한 반환 (유저 권한이 여러개?)
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }
}
