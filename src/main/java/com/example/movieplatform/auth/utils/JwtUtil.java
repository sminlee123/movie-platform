package com.example.movieplatform.auth.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtUtil {

    // 개발자가 만드는 시크릿 값
    // 나중에 옮기기
    private static final String SECRET = "bW92aWVwbGF0Zm9ybWp3dHRva2Vuc2VjcmV0a2V5";

    // 키 디코딩
    private static final Key KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    // 권한 넣기 (열거형으로 받아서 넣기?)
    public String generateAccessToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 만료 1시간
                .signWith(KEY)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
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
    public boolean validateToken(String token) {
        try {
            Claims claims = parseClaims(token);  // 서명 검증 포함
            return true;
        } catch (JwtException | IllegalArgumentException e) { // JwtToken 최상위 예외
            log.debug("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    // 유저 이름 반환
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // 유저 권한 반환 (유저 권한이 여러개?)
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }
}
