package com.example.movieplatform.common.config;

//import com.example.movieplatform.auth.filter.JwtCookieFilter;
import com.example.movieplatform.auth.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // 모든 요청
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/signup", "/users/login", "/users/delete").permitAll()
                        .requestMatchers("/auth/**", "/users").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 커스텀 로그인 폼 경로 지정
                        .loginProcessingUrl("/auth/login") // 로그인 처리
                        .usernameParameter("email")  // 로그인 폼 email 필드명 맞춤
                        .successHandler(customLoginSuccessHandler)
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 람다 방식으로 CSRF 비활성화

        return http.build();
    }
}
