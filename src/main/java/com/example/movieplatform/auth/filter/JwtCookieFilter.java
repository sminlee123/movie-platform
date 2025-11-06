package com.example.movieplatform.auth.filter;

import com.example.movieplatform.auth.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
//    private final AntPathMatcher pathMatcher = new AntPathMatcher();

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return pathMatcher.match("/api/logout", request.getRequestURI()) &&
//                "POST".equalsIgnoreCase(request.getMethod());
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getTokenFromHeader(request);

        log.debug("accessToken: {}", accessToken);

        if (StringUtils.hasText(accessToken)) {
            try {
                authenticateUser(accessToken);
            } catch (ExpiredJwtException e) {
                log.warn("Access token has expired");
                sendErrorResponse(response, "EXPIRED");
                return;
            } catch (Exception e) {
                log.warn("InValid access token");
                sendErrorResponse(response, "INVALID");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // 헤더에서 AccessToken 추출
    private String getTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 인증 정보를 컨텍스트 홀더에 저장
    private void authenticateUser(String token) {
        String userEmail = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getRole(token);

        // 인증 객체 생성자에 들어갈 권한 컬렉션
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + role));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userEmail, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void sendErrorResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", errorCode));
    }
}