package com.example.movieplatform.auth.handler;

import com.example.movieplatform.auth.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginFailHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    // 로그인 실패 핸들러

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(
                "Authentication Failed",
                "올바르지 않은 아이디 혹은 비밀번호 입니다."
        );

        log.info("로그인 실패");

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
