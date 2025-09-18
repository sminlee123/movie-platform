package com.example.movieplatform.screen.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ScreenAlreadyExistsException extends ApiException {

    private static final String MESSAGE = "이미 존재하는 상영관입니다.";

    public ScreenAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value(), MESSAGE);
    }
}
