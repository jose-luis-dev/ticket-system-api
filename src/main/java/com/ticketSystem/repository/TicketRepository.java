package com.ticketSystem.repository;

import com.ticketSystem.enums.EstadoRegistro;
import com.ticketSystem.model.Ticket;

import java.util.ArrayList;
import java.util.List;


// Implementación en memoria — reemplazada por TicketRepositoryJdbc
// Conservada como referencia de la versión v1.0

public class TicketRepository implements ITicketRepository {

    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public List<Ticket> listarTickets() {
        return new ArrayList<>(tickets);
    }

    @Override
    public Ticket buscarPorId(int id) {
        return tickets.stream()
                .filter(t -> t.getIdTicket() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void guardar(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public void actualizar(Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++){
            Ticket ticketActual = tickets.get(i);
            if (ticketActual.getIdTicket() == ticket.getIdTicket()){
                tickets.set(i, ticket);
                return;
            }
        }
    }

    @Override
    public void eliminarLogico(int id) {
        Ticket ticket = buscarPorId(id);
        if (ticket != null){
            ticket.setEstadoOperacionalTicket(EstadoRegistro.INACTIVO);
            actualizar(ticket);
        }
    }
}
