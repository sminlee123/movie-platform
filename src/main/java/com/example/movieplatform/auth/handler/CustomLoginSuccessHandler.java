package com.example.movieplatform.auth.handler;

import com.example.movieplatform.auth.domain.CustomUserDetails;
import com.example.movieplatform.auth.utils.JwtUtil;
import com.example.movieplatform.user.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    // 로그인 성공 시 쿠키 심는 핸들러

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("Handler Start");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        log.info(user.toString());

        String userRole = user.getIsAdmin() ? "ADMIN" : "MEMBER";

        Cookie accessToken = new Cookie("ACCESSTOKEN",
                jwtUtil.generateAccessToken(user.getEmail(), userRole));
        Cookie refreshToken = new Cookie("REFRESHTOKEN",
                jwtUtil.generateRefreshToken(user.getEmail()));

        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/");

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
