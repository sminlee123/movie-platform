package com.example.movieplatform.showinginfo.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ShowingInfoAlreadyExistsException extends ApiException {

    private static final String MESSAGE = "해당 상영일에는 이미 상영정보가 있습니다.";

    public ShowingInfoAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value(), MESSAGE);
    }
}
