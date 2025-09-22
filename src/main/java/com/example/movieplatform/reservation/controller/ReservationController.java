package com.example.movieplatform.reservation.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.reservation.service.ReservationService;
import com.example.movieplatform.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/reservations")
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
}
