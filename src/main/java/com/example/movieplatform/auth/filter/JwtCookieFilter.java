package com.example.movieplatform.auth.filter;

import com.example.movieplatform.auth.utils.JwtUtil;
import com.example.movieplatform.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getTokenFromCookies(request, "ACCESSTOKEN");
        String refreshToken = getTokenFromCookies(request, "REFRESHTOKEN");

        if (accessToken != null && jwtUtil.validateToken(accessToken)) {

        } else if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            String userEmail = jwtUtil.getUserEmail(refreshToken);
            String role = userService.getUserRole(userEmail);

            // 리프레시 토큰으로 엑세스 토큰 재발급
            String newToken = jwtUtil.generateAccessToken(userEmail, role);

            Cookie cookie = new Cookie("ACCESSTOKEN", newToken);
            response.addCookie(cookie);
        }
    }

    // 쿠키 추출 메서드
    private String getTokenFromCookies(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
