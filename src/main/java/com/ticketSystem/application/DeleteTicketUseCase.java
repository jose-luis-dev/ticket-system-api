package com.ticketSystem.application;

import com.ticketSystem.model.Usuario;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class DeleteTicketUseCase {
    private final TicketService ticketService;

    public DeleteTicketUseCase(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public boolean execute (int idTicket, Usuario usuarioActual){
        return ticketService.eliminarTicket(idTicket, usuarioActual);
    }
}
