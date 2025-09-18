package com.example.movieplatform.screen.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class SeatNotFoundException extends ApiException {

    private static final String MESSAGE = "해당 좌석은 존재하지 않습니다.";

    public SeatNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), MESSAGE);
    }
}
