package com.example.movieplatform.auth.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class LoginFailException extends ApiException {

    private static final String MESSAGE = "올바르지 않은 아이디 혹은 비밀번호 입니다.";

    public LoginFailException() {
        super(HttpStatus.UNAUTHORIZED.value(), MESSAGE);
    }
}
