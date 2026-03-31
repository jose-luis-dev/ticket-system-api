package com.ticketSystem.application;

import com.ticketSystem.model.Ticket;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListTicketUseCase {
    private final TicketService ticketService;

    public ListTicketUseCase(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public List<Ticket> execute() {
        return ticketService.listarTicket();
    }
}
