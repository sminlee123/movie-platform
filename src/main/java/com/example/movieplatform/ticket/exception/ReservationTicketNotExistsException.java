package com.example.movieplatform.ticket.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ReservationTicketNotExistsException extends ApiException {

    private static final String MESSAGE = "해당 예매에 티켓이 존재하지 않습니다.";

    public ReservationTicketNotExistsException() {
        super(HttpStatus.NOT_FOUND.value(), MESSAGE);
    }
}
