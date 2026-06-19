package com.ticketSystem.service;

import com.ticketSystem.enums.TipoAsignacion;
import com.ticketSystem.exception.InvalidOperationException;
import com.ticketSystem.model.Asignacion;
import com.ticketSystem.model.Usuario;
import com.ticketSystem.repository.IAsignacionRepository;
import com.ticketSystem.repository.ITicketRepository;
import com.ticketSystem.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionService {

    private final IAsignacionRepository asignacionRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ITicketRepository ticketRepository;
    private final EmailService emailService;

    public AsignacionService(IAsignacionRepository asignacionRepository,
                             IUsuarioRepository usuarioRepository,
                             ITicketRepository ticketRepository,
                             EmailService emailService) {
        this.asignacionRepository = asignacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
        this.emailService = emailService;
    }

    // -- Asignación automatica ------

    public Asignacion asignarAutomaticamente(Integer ticketId, String tituloTicket) {

        // Encontrar agente menos carga
        Long agenteId = asignacionRepository.findAgentWithLeastTickets()
                .orElseThrow(() -> new InvalidOperationException(
                        "No hay agentes disponibles para asignar el ticket"));

        // Obtener datos del agente para el email
        Usuario agente = usuarioRepository.findById(agenteId)
                .orElseThrow(() -> new InvalidOperationException(
                        "Agente no encontrado con id: " + agenteId));

        // Registrar asignacion DB
        Asignacion asignacion = new Asignacion(
                ticketId,
                agenteId,
                null,  // null = asignado por el sistema
                TipoAsignacion.ASIGNACION_INICIAL,
                "Asignación automatica por balance de carga",
                null
        );
        Asignacion guardada = asignacionRepository.save(asignacion);

        // Actualizar agente_id en tabla tickets
        ticketRepository.updateAgenteId(ticketId, agenteId);

        // Notificar al nuevo agente por email
        emailService.enviarNotificacionAsignacion(
                agente.getEmail(),
                agente.getEmail(),
                ticketId,
                tituloTicket
        );

        return guardada;

    }

    // --- Reasignacion manual ------

    public Asignacion reasignar(Integer ticketId,
                                String tituloTicket,
                                Long nuevoAgenteId,
                                Long asignadoPorId,
                                String motivo) {

        // Verificar si existe ticket
        ticketRepository.buscarPorId(ticketId);

        // verificar el nuevo agente existe y está activo
        Usuario nuevoAgente = usuarioRepository.findById(nuevoAgenteId)
                .orElseThrow(() -> new InvalidOperationException(
                        "Agente destino no encontrado con id: " + nuevoAgenteId));

        // verificar que no se reasigna al mismo agente actual
        asignacionRepository.findCurrentByTicketId(ticketId).ifPresent(actual -> {
            if (actual.getAgenteId().equals(nuevoAgenteId)) {
                throw new InvalidOperationException(
                        "El ticket ya está asignado a este agente");
            }
        });

        // Registrar reasignacion DB
        Asignacion asignacion = new Asignacion(
                ticketId,
                nuevoAgenteId,
                asignadoPorId,  // quién hace la reasignación
                TipoAsignacion.REASIGNACION,
                motivo,
                null
        );
        Asignacion guardada = asignacionRepository.save(asignacion);

        // Actualizar agente_id
        ticketRepository.updateAgenteId(ticketId, nuevoAgenteId);

        // Notificar al nuevo agente por email
        emailService.enviarNotificacionReasignacion(
                nuevoAgente.getEmail(),
                nuevoAgente.getNombre(),
                ticketId,
                tituloTicket,
                motivo
        );

        return guardada;
    }

    // -- Historial

    public List<Asignacion> obtenerHistorial(Integer ticketId) {
        ticketRepository.buscarPorId(ticketId);
        return asignacionRepository.findHistoryByTicketId(ticketId);
    }


}
