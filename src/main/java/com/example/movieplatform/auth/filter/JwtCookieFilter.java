    package com.example.movieplatform.auth.filter;

    import com.example.movieplatform.auth.utils.JwtUtil;
    import com.example.movieplatform.user.service.UserService;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.Cookie;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;
    import java.util.List;

    @Slf4j
    @Component
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
                authenticateUser(accessToken);
            } else if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
                String userEmail = jwtUtil.getUserEmail(refreshToken);
                log.info(userEmail);
                String role = userService.getUserRole(userEmail);

                // 리프레시 토큰으로 엑세스 토큰 재발급
                String newToken = jwtUtil.generateAccessToken(userEmail, role);

                Cookie cookie = new Cookie("ACCESSTOKEN", newToken);
                response.addCookie(cookie);

                authenticateUser(newToken);
            } else  {
                // 토큰 둘 다 없을때 컨텍스트 한번 초기화
                // TODO 초기화만 하는게 맞을까
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
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

        // 인증 정보를 컨텍스트 홀더에 저장
        private void authenticateUser(String token) {
            String userEmail = jwtUtil.getUserEmail(token);
            String role = jwtUtil.getRole(token);

            // 인증 객체 생성자에 들어갈 권한 컬렉션
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userEmail, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
