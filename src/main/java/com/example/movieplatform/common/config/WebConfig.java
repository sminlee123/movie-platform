package com.example.movieplatform.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 애플리케이션의 모든 API 경로에 대해 CORS 설정을 적용합니다.
                .allowedOrigins("http://localhost:3000") // 리액트 앱의 주소만 요청을 허용합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용할 HTTP 메서드를 지정합니다.
                .allowedHeaders("*") // 모든 HTTP 헤더를 허용합니다.
                .allowCredentials(true) // 쿠키나 인증 헤더 등 자격 증명 정보를 포함한 요청을 허용합니다.
                .maxAge(3600); // 브라우저가 CORS 설정을 캐시하는 시간(초)을 설정합니다.
    }
}
