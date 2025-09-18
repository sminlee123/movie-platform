package com.example.movieplatform.screen.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class SeatNotAvailableException extends ApiException {

    private static final String MESSAGE = "해당 좌석은 예매완료 상태입니다.";

    public SeatNotAvailableException() {
        super(HttpStatus.BAD_REQUEST.value(),  MESSAGE);
    }
}
