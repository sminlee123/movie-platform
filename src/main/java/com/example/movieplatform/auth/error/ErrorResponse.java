package com.example.movieplatform.auth.error;

public record ErrorResponse(
        String error,
        String message
) {
}
