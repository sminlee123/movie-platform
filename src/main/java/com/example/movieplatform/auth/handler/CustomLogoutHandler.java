package com.example.movieplatform.auth.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    // 로그아웃 쿠키 삭제 핸들러

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // 리프레시 삭제
        Cookie refreshToken = new Cookie("REFRESHTOKEN", null);
        refreshToken.setPath("/");
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(0);
        response.addCookie(refreshToken);

        // CSRF 쿠키 삭제
        Cookie csrfToken = new Cookie("XSRF-TOKEN", null);
        csrfToken.setPath("/");
        csrfToken.setHttpOnly(false);
        csrfToken.setMaxAge(0);
        response.addCookie(csrfToken);

        // 컨텍스트에 담긴 정보 초기화
        SecurityContextHolder.clearContext();
    }
}