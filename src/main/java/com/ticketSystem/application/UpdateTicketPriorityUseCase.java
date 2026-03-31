package com.ticketSystem.application;

import com.ticketSystem.enums.Prioridad;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class UpdateTicketPriorityUseCase {
    private final TicketService ticketService;

    public UpdateTicketPriorityUseCase(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public boolean execute(int idTicket, Prioridad prioridad){
        return ticketService.cambiarPrioridadTicket(idTicket, prioridad);
    }
}
