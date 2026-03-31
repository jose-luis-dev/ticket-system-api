package com.ticketSystem.application;

import com.ticketSystem.enums.EstadoOperacional;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class UpdateTicketStatusUseCase {
    private final TicketService ticketService;

    public UpdateTicketStatusUseCase(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public boolean execute(int idTicket, EstadoOperacional nuevoEstado) {
        return ticketService.cambiarEstadodeTicket(idTicket, nuevoEstado);
    }
}
