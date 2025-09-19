package com.example.movieplatform.showinginfo.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ShowingInfoNotExistsException extends ApiException {

    private final static String MESSAGE = "해당 상영정보는 존재하지 않습니다.";

    public ShowingInfoNotExistsException() {
        super(HttpStatus.NOT_FOUND.value(),  MESSAGE);
    }
}
