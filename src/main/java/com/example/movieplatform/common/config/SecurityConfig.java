package com.example.movieplatform.common.config;

import com.example.movieplatform.auth.filter.JwtCookieFilter;
import com.example.movieplatform.auth.handler.CustomLoginFailHandler;
import com.example.movieplatform.auth.handler.CustomLoginSuccessHandler;
import com.example.movieplatform.auth.handler.CustomLogoutHandler;
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
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/movies/search").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 커스텀 로그인 폼 경로 지정
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
                );


        http.addFilterBefore(jwtCookieFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
