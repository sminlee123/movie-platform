package com.example.movieplatform.movie.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class MovieNotExistsException extends ApiException {

    private static final String MESSAGE = "존재하지 않는 영화입니다.";

    public MovieNotExistsException() {
        super(HttpStatus.NO_CONTENT.value(), MESSAGE);
    }
}
