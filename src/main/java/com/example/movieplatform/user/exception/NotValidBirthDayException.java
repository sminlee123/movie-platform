package com.example.movieplatform.user.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class NotValidBirthDayException extends ApiException {

    private static final String MESSAGE = "생일은 현재 날짜 이후일 수 없습니다.";

    public NotValidBirthDayException() {
        super(HttpStatus.BAD_REQUEST.value(), MESSAGE);
    }
}
