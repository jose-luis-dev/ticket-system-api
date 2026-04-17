package com.ticketSystem.application;

import com.ticketSystem.application.validators.TicketStateTransitionValidator;
import com.ticketSystem.enums.EstadoOperacional;
import com.ticketSystem.exception.InvalidTransicionException;
import com.ticketSystem.model.Ticket;
import com.ticketSystem.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class UpdateTicketStatusUseCase {
    private final TicketService ticketService;

    public UpdateTicketStatusUseCase(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public boolean execute(int idTicket, EstadoOperacional nuevoEstado) {

        // 1. Obtener ticket - si no existe, TicketService ya lanza TicketNotFoundException
        Ticket ticket = ticketService.buscarTicketId(idTicket);

        //2. Validar transición contra las reglas de negocio
        EstadoOperacional estadoActual = ticket.getEstadoOperacionalActual();

        if (!TicketStateTransitionValidator.esValida(estadoActual, nuevoEstado)) {
            throw new InvalidTransicionException(estadoActual, nuevoEstado);
        }

        // 3. Transición valida - ejecuta
        return ticketService.cambiarEstadodeTicket(idTicket, nuevoEstado);
    }
}
