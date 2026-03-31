package com.ticketSystem.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(int id) {
        super("Ticket no encontrado con id: " +  id);
    }
}
