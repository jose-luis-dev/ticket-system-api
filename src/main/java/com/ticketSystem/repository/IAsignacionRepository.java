package com.ticketSystem.repository;

import com.ticketSystem.model.Asignacion;

import java.util.List;
import java.util.Optional;

public interface IAsignacionRepository {

    Asignacion save(Asignacion asignacion);

    Optional<Asignacion> findCurrentByTicketId(Integer ticketId);

    List<Asignacion> findHistoryByTicketId(Integer ticketId);

    Optional<Long> findAgentWithLeastTickets();
}
