package com.example.movieplatform.screen.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ScreenNotFoundException extends ApiException {

    private static final String MESSAGE = "해당 스크린은 존재하지 않습니다.";

    public ScreenNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), MESSAGE);
    }
}
