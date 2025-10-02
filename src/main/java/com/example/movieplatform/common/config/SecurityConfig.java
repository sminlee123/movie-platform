package com.example.movieplatform.common.config;

import com.example.movieplatform.auth.filter.JwtCookieFilter;
import com.example.movieplatform.auth.handler.CustomLoginFailHandler;
import com.example.movieplatform.auth.handler.CustomLoginSuccessHandler;
import com.example.movieplatform.auth.handler.CustomLogoutHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailHandler customLoginFailHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final JwtCookieFilter jwtCookieFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // 모든 요청
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/auth/**", "/users").permitAll()
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/api/movies/search").permitAll()
                        .requestMatchers("/movies/**").permitAll()
                        .requestMatchers("/api/user/me").authenticated()

                        .requestMatchers("/api/signup").permitAll() // 회원가입 열어놓음
                        .requestMatchers("/api/mypage/**").authenticated()
                        .requestMatchers("/api/reservations/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // 로그인 처리
                        .usernameParameter("email")
                        .successHandler(customLoginSuccessHandler)
                        .failureHandler(customLoginFailHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessUrl("/")
                )
                .csrf(csrf -> csrf.disable()) // 람다 방식으로 CSRF 비활성화

                // 세션 STATELESS 설정
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> {
                    // 인가(Authorization) 실패 시 (권한 없는 경우)
                    exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    });
                    // 인증(Authentication) 실패 시 (로그인 안 한 경우)
                    exception.authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    });
                });


        http.addFilterBefore(jwtCookieFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
