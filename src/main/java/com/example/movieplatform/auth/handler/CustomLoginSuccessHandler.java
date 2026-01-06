package com.example.movieplatform.auth.handler;

import com.example.movieplatform.auth.domain.CustomUserDetails;
import com.example.movieplatform.auth.utils.CsrfUtil;
import com.example.movieplatform.auth.utils.JwtUtil;
import com.example.movieplatform.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    // 로그인 성공 시 세팅 핸들러

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final CsrfUtil csrfUtil;

    public static final int REFRESH_AGE = 7 * 24 * 60 * 60;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("Handler Start");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String roleForToken = role.replace("ROLE_", "");

        // 엑세스
        String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername(), roleForToken);

        // 리프레시
        Cookie refreshToken = new Cookie("REFRESHTOKEN",
                jwtUtil.generateRefreshToken(userDetails.getUsername()));
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(REFRESH_AGE);
        refreshToken.setSecure(true);
        response.addCookie(refreshToken);

        Cookie csrfToken = new Cookie("XSRF-TOKEN",
                csrfUtil.generateCsrfToken());
        csrfToken.setHttpOnly(false);
        csrfToken.setPath("/");
        csrfToken.setMaxAge(REFRESH_AGE);
        csrfToken.setSecure(true);
        response.addCookie(csrfToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);

        String jsonResponse = objectMapper.writeValueAsString(tokenMap);

        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse);
        writer.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
