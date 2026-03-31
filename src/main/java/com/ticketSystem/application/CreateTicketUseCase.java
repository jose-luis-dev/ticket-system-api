package com.ticketSystem.application;

import com.ticketSystem.model.Ticket;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class CreateTicketUseCase {

    private final TicketService ticketService;

    public CreateTicketUseCase(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public Ticket execute(String titulo, String descripcion) {
        return ticketService.crearTicket(titulo, descripcion);
    }
}
