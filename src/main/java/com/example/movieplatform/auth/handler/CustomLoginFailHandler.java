package com.example.movieplatform.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomLoginFailHandler implements AuthenticationFailureHandler {

    // 로그인 실패 핸들러

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "올바르지 않은 아이디 혹은 비밀번호 입니다.";

        response.sendRedirect("/login?error=" + URLEncoder.encode(errorMessage, "UTF-8"));

    }
}
