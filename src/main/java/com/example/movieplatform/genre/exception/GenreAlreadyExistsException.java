package com.example.movieplatform.genre.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class GenreAlreadyExistsException extends ApiException {

    private static final String MESSAGE = "이미 존재하는 장르입니다.";

    public GenreAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value(), MESSAGE);
    }
}
