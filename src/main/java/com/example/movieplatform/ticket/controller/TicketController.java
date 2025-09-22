package com.example.movieplatform.ticket.controller;

import com.example.movieplatform.ticket.domain.request.TicketBuyRequest;
import com.example.movieplatform.ticket.service.TicketService;
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

    @PostMapping
    public String bookTicket(@ModelAttribute TicketBuyRequest request) {
        ticketService.ticketBuy(request);
        return "redirect:/admin/screens";
    }
}
