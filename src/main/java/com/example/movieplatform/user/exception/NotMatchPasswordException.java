package com.example.movieplatform.user.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class NotMatchPasswordException extends ApiException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public NotMatchPasswordException() {
        super(HttpStatus.BAD_REQUEST.value(), MESSAGE);
    }
}
