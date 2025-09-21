package com.example.movieplatform.ticket.domain.request;

public record TicketBuyRequest (
       Long showingInfoId,
       String seatName
) {
}
