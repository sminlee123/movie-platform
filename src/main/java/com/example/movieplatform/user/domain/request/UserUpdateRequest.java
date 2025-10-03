package com.example.movieplatform.user.domain.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserUpdateRequest (

        @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
        @Size(min = 2, message = "사용자 이름은 2자 이상이어야 합니다.")
        @Size(max = 30, message = "사용자 이름은 30자 이하여야 합니다.")
        String name,

        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리 숫자로만 입력해주세요.")
        String phoneNumber,

        @NotNull(message = "생년월일은 필수 입력 값입니다.")
        @Past(message = "생년월일은 현재 날짜보다 이전이어야 합니다.")
        LocalDate birthDay
) {
}