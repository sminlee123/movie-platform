package com.example.movieplatform.user.domain.request;

import java.time.LocalDate;

public record UserUpdateRequest (
        String userName,

        String phoneNumber,

        LocalDate birthDay
) {
}