package com.example.movieplatform.screen.service;

public interface SeatService {
//    void generateSeats(SeatGenerateRequest request);
    void generateSeats(Long screenId, int rows, int cols);
//    void deleteSeats(SeatDeleteRequest request);
    Long countAllSeats(Long screenId);
}
