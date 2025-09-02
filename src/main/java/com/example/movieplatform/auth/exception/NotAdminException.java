package com.example.movieplatform.auth.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class NotAdminException extends ApiException {

    private static final String MESSAGE = "권한이 부족합니다.";

    public NotAdminException() {
        super(HttpStatus.FORBIDDEN.value(), MESSAGE);
    }
}
