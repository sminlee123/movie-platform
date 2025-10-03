package com.example.movieplatform.user.domain.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserCreateRequest (

        @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
        @Size(min = 2, message = "사용자 이름은 2자 이상이어야 합니다.")
        @Size(max = 30, message = "사용자 이름은 30자 이하여야 합니다.")
        String username,

        @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @Size(max  = 30, message = "이메일은 30자 이하여야 합니다.") String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        @Size(max = 20, message = "비밀번호는 20자 이하여야 합니다.")
        String password,

        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리 숫자로만 입력해주세요.") // 정규식
        String phoneNumber,

        @NotNull(message = "생년월일은 필수 입력 값입니다.")
        @Past(message = "생년월일은 현재 날짜보다 이전이어야 합니다.")
        LocalDate birthDay
) {
}
