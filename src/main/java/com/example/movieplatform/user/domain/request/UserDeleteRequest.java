package com.example.movieplatform.user.domain.request;

public record UserDeleteRequest (
    String email,
    String password
) {
}