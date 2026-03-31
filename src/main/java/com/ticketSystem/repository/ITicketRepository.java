package com.ticketSystem.repository;

import com.ticketSystem.model.Ticket;

import java.util.List;

public interface ITicketRepository {
    List<Ticket> listarTickets();
    Ticket buscarPorId(int id);
    void guardar(Ticket ticket);
    void actualizar(Ticket ticket);
    void eliminarLogico(int id);
}
