package com.ticketSystem.application;

import com.ticketSystem.enums.EstadoOperacional;
import com.ticketSystem.enums.Prioridad;
import com.ticketSystem.exception.InvalidOperationException;
import com.ticketSystem.model.Ticket;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class UpdateTicketPriorityUseCase {
    private final TicketService ticketService;

    public UpdateTicketPriorityUseCase(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public boolean execute(int idTicket, Prioridad prioridad){
        //1. Obtener ticket
        Ticket ticket = ticketService.buscarTicketId(idTicket);

        EstadoOperacional estadoActual = ticket.getEstadoOperacionalActual();

        if (esEstadoFinal(estadoActual)) {
            throw new InvalidOperationException(estadoActual);
        }
        // 3. Transicion valida - ejecuta
        return ticketService.cambiarPrioridadTicket(idTicket, prioridad);
    }

    private boolean esEstadoFinal(EstadoOperacional estado){
        return estado == EstadoOperacional.CERRADO || estado == EstadoOperacional.CANCELADO;
    }
}
