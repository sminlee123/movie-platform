package com.example.movieplatform.ticket.controller;

import com.example.movieplatform.auth.utils.AuthenticationUtil;
import com.example.movieplatform.ticket.domain.request.TicketBuyRequest;
import com.example.movieplatform.ticket.service.TicketService;
import com.example.movieplatform.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final AuthenticationUtil authenticationUtil;

    @PostMapping
    public String bookTicket(@ModelAttribute TicketBuyRequest request) {
        User user = authenticationUtil.getCurrentUser();
        ticketService.ticketBuy(request, user);
        return "redirect:/admin/screens";
    }
}
