package com.example.movieplatform.reservation.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.reservation.domain.response.ReservationDetailResponse;
import com.example.movieplatform.reservation.domain.response.ReservationResponse;
import com.example.movieplatform.reservation.service.ReservationService;
import com.example.movieplatform.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthenticationUtil authenticationUtil;

    @PostMapping
    public String createReservation(@ModelAttribute ReservationRequest request) {
        User user = authenticationUtil.getCurrentUser();
        reservationService.createReservation(request, user);
        return "redirect:/admin/screens";
    }

//    @GetMapping("/{id}")
//    public String showReservationDetail(@PathVariable Long id, Model model) {
//        ReservationDetailResponse detail = reservationService.getReservationDetails(id);
//        model.addAttribute("detail", detail);
//        return "users/reservationDetail";
//    }

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
