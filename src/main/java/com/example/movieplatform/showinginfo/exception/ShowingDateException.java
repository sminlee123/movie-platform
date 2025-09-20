package com.example.movieplatform.showinginfo.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class ShowingDateException extends ApiException {

    private static final String MESSAGE = "상영일은 현재보다 이전일 수 없습니다.";

    public ShowingDateException() {
        super(HttpStatus.BAD_REQUEST.value(), MESSAGE);
    }
}
