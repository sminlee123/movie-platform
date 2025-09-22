package com.example.movieplatform.reservation.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.reservation.domain.request.ReservationRequest;
import com.example.movieplatform.reservation.domain.response.ReservationResponse;
import com.example.movieplatform.reservation.service.ReservationService;
import com.example.movieplatform.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public String showReservations(@PageableDefault(size = 10, page = 0) Pageable pageable,
                                   Model model) {
        User user = authenticationUtil.getCurrentUser();
        Page<ReservationResponse> reservations = reservationService.getReservationsByUserId(user.getId(), pageable);
        model.addAttribute("reservations", reservations);
        model.addAttribute("userName", user.getUserName());
        return "users/reservations";
    }

    @PostMapping
    public String createReservation(@ModelAttribute ReservationRequest request) {
        User user = authenticationUtil.getCurrentUser();
        reservationService.createReservation(request, user);
        return "redirect:/admin/screens";
    }
}
