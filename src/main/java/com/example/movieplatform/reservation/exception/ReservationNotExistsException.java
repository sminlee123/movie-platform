package com.example.movieplatform.reservation.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ReservationNotExistsException extends ApiException {

    private static final String MESSAGE = "해당 예매가 존재하지 않습니다.";

    public ReservationNotExistsException() {
        super(HttpStatus.NOT_FOUND.value(), MESSAGE);
    }
}
