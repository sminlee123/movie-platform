package com.example.movieplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MoviePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviePlatformApplication.class, args);
    }

}
