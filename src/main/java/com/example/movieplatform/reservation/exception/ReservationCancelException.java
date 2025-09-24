package com.example.movieplatform.reservation.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ReservationCancelException extends ApiException {

    private static final String MESSAGE = "해당 예매는 취소할 수 없는 상태입니다.";

    public ReservationCancelException() {
        super(HttpStatus.BAD_REQUEST.value(), MESSAGE);
    }
}