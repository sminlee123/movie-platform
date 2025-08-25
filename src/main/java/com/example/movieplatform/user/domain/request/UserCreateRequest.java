package com.example.movieplatform.user.domain.request;

import java.time.LocalDate;

public record UserCreateRequest (
        String username,

        String email,

        String password,

        String phoneNumber,

        LocalDate birthDay
) {
}
