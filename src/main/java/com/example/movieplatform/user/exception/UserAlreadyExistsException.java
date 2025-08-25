package com.example.movieplatform.user.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value(), MESSAGE);
    }
}
