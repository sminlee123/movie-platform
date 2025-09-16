package com.example.movieplatform.movie.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class MovieAlreadyExistsException extends ApiException {

    private static final String MESSAGE = "이미 존재하는 영화입니다.";

    public MovieAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value(), MESSAGE);
    }
}
