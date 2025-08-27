package com.example.movieplatform.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // 모든 요청
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/signup", "/users/login", "/users/delete").permitAll()
                        .requestMatchers("/auth/**", "/users").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()); // 람다 방식으로 CSRF 비활성화

        return http.build();
    }
}
