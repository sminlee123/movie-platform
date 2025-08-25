package com.example.movieplatform.user.domain.request;

public record UserLoginRequest (
        String email,
        String password
) {
}
