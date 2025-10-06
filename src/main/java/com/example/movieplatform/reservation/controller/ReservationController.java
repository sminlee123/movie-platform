package com.example.movieplatform.reservation.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.reservation.domain.response.ReservationDetailResponse;
import com.example.movieplatform.reservation.service.ReservationService;
import com.example.movieplatform.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthenticationUtil authenticationUtil;

    @PostMapping
    public ResponseEntity<ReservationDetailResponse> createReservation(@RequestBody ReservationRequest request) {
        User user = authenticationUtil.getCurrentUser();
        ReservationDetailResponse response = reservationService.createReservation(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDetailResponse> showReservationDetail(@PathVariable Long id) {
        ReservationDetailResponse detail = reservationService.getReservationDetails(id);
        return ResponseEntity.ok(detail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

}
