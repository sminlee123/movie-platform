package com.example.movieplatform.user.domain.response;

import java.time.LocalDate;

public record UserDetailResponse (
        String name,
        String email,
        String phoneNumber,
        LocalDate birthDay
) {
}
