package com.ticketSystem.controller.dto;

import com.ticketSystem.model.Ticket;

public class TicketMapper {

    public static TicketResponse toResponse (Ticket ticket){
        return new TicketResponse(
                ticket.getIdTicket(),
                ticket.getTitulo(),
                ticket.getDescripcion()
        );
    }
}
