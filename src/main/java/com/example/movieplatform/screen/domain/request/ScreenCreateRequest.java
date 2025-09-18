package com.example.movieplatform.screen.domain.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ScreenCreateRequest(
        String name,

        @Min(value = 1)
        @Max(value = 10)
        int rows,

        @Min(value = 1)
        @Max(value = 10)
        int cols
) {
}