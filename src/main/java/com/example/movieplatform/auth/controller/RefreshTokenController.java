package com.example.movieplatform.auth.controller;

import com.example.movieplatform.auth.utils.JwtUtil;
import com.example.movieplatform.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.movieplatform.auth.handler.CustomLoginSuccessHandler.REFRESH_AGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class RefreshTokenController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            @CookieValue(value = "REFRESHTOKEN", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        log.info("POST /api/auth/refresh started.");

        if (refreshToken == null) {
            log.warn("Refresh token not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing.");
        }

        String userEmail;

        try {
            jwtUtil.validateToken(refreshToken);
            userEmail = jwtUtil.getUserEmail(refreshToken);
        } catch (ExpiredJwtException e) {
            clearRefreshTokenCookie(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Expired refresh token.");
        } catch (Exception e) {
            clearRefreshTokenCookie(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        }

        String role = userService.getUserRole(userEmail);
        String newAccessToken = jwtUtil.generateAccessToken(userEmail, role);

        String newRefreshToken = jwtUtil.generateRefreshToken(userEmail);
        addRefreshToken(response, newRefreshToken); // 새 리프레시 토큰 추가

        log.info("AccessToken and RefreshToken Issue {}.", userEmail);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    private void addRefreshToken(HttpServletResponse response, String newRefreshToken) {
        log.info("Adding RefreshToken");

        Cookie refreshToken = new Cookie("REFRESHTOKEN", newRefreshToken);
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(REFRESH_AGE);
        refreshToken.setSecure(true);

        response.addCookie(refreshToken);
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        log.warn("Clearing RefreshToken");

        Cookie cookie = new Cookie("REFRESHTOKEN", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}