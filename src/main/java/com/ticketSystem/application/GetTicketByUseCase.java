package com.ticketSystem.application;

import com.ticketSystem.model.Ticket;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class GetTicketByUseCase {
    private final TicketService ticketService;

    public GetTicketByUseCase (TicketService ticketService){
        this.ticketService = ticketService;
    }

    public Ticket execute(int idTicket){
        return ticketService.buscarTicketId(idTicket);
    }
}
