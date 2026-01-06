package com.example.movieplatform.auth.controller;

import com.example.movieplatform.auth.utils.CsrfUtil;
import com.example.movieplatform.auth.utils.JwtUtil;
import com.example.movieplatform.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.movieplatform.auth.handler.CustomLoginSuccessHandler.REFRESH_AGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class RefreshTokenController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CsrfUtil csrfUtil;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            @CookieValue(value = "REFRESHTOKEN", required = false) String refreshToken,
            @CookieValue(value = "XSRF-TOKEN", required = false) String csrfToken,
            @RequestHeader(value = "X-XSRF-TOKEN", required = false) String csrfHeader,
            HttpServletResponse response
    ) {
        log.info("POST /api/auth/refresh started.");

        if (csrfToken == null || csrfHeader == null || !csrfToken.equals(csrfHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid csrf token");
        }

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

        String newCsrfToken = csrfUtil.generateCsrfToken();
        addCsrfToken(response, newCsrfToken);

        log.info("AccessToken and RefreshToken Issue {}.", userEmail);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    private void addCsrfToken(HttpServletResponse response, String newCsrfToken) {
        log.info("Adding csrf token.");

        Cookie csrfToken = new Cookie("XSRF-TOKEN", newCsrfToken);
        csrfToken.setHttpOnly(false);
        csrfToken.setPath("/");
        csrfToken.setMaxAge(REFRESH_AGE);
        csrfToken.setSecure(true);

        response.addCookie(csrfToken);
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