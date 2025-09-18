package com.example.movieplatform.screen.controller;

import com.example.movieplatform.screen.domain.request.SeatDeleteRequest;
import com.example.movieplatform.screen.domain.request.SeatGenerateRequest;
import com.example.movieplatform.screen.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/seats")
@RequiredArgsConstructor
public class AdminSeatController {

    private final SeatService seatService;

//    @PostMapping
//    public ResponseEntity<String> generateSeats(@RequestBody @Valid SeatGenerateRequest request) {
//        seatService.generateSeats(request);
//        return ResponseEntity.ok("좌석이 성공적으로 생성되었습니다.");
//    }

    // 임시
    @PostMapping("/book")
    public ResponseEntity<String> bookSeat(@RequestBody SeatDeleteRequest request) {
        log.info(request.toString());
        seatService.deleteSeats(request);
        return ResponseEntity.ok("좌석 예매가 완료되었습니다.");
    }

}
