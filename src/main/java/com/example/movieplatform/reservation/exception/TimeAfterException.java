package com.example.movieplatform.reservation.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class TimeAfterException extends ApiException {

    private static final String MESSAGE = "현재 상영중이거나 상영완료된 영화입니다.";

    public TimeAfterException() {
        super(HttpStatus.BAD_REQUEST.value(), MESSAGE);
    }
}
