package com.example.movieplatform.genre.exception;

import com.example.movieplatform.common.advice.ApiException;
import org.springframework.http.HttpStatus;

public class GenreNotExistsException extends ApiException {

    private static final String MESSAGE = "존재하지 않는 장르입니다.";

    public GenreNotExistsException() {
        super(HttpStatus.NOT_FOUND.value(), MESSAGE);
    }
}
