package com.example.movieplatform.screen.service;

import com.example.movieplatform.screen.domain.request.SeatDeleteRequest;
import com.example.movieplatform.screen.domain.request.SeatGenerateRequest;

public interface SeatService {
    void generateSeats(SeatGenerateRequest request);
    void generateSeats(Long screenId, int rows, int cols);
    void deleteSeats(SeatDeleteRequest request);
    Long countAllSeats(Long screenId);
    Long countAvailableSeats(Long screenId);
}
