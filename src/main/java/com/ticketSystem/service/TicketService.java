package com.ticketSystem.service;

import com.ticketSystem.enums.EstadoOperacional;
import com.ticketSystem.enums.Prioridad;
import com.ticketSystem.enums.RolUsuario;
import com.ticketSystem.exception.TicketNotFoundException;
import com.ticketSystem.model.Ticket;
import com.ticketSystem.model.Usuario;
import com.ticketSystem.repository.ITicketRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {

    private final ITicketRepository ticketRepository;

    public TicketService(ITicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    // Creacion de ticket
    public Ticket crearTicket(String titulo, String descripcion ){

        if (titulo == null || titulo.isBlank() ){
            throw new IllegalArgumentException("El titulo es obligatorio");
        }
        if (descripcion == null){
            descripcion = "";
        }
        // Se crea una instancia de objeto Ticket para los datos ingresados.
        Ticket nuevoTicket = new Ticket(titulo, descripcion);
        //Se agrega al repositorio con el metodo guardar
        ticketRepository.guardar(nuevoTicket);
        return nuevoTicket;
    }

    // Mostrar la lista de tickets
    public List<Ticket> listarTicket(){
        return ticketRepository.listarTickets();
    }

    // Busqueda por Id del ticket
    public Ticket buscarTicketId(int idTicket){
        Ticket ticket = ticketRepository.buscarPorId(idTicket);
        if (ticket == null) {
            throw new TicketNotFoundException(idTicket);
        }
        return ticket;
    }

    // Eliminar el ticket "Solo ADMIN"
    public boolean eliminarTicket(int idTicket, Usuario usuarioActual){

        if (usuarioActual.getRol() != RolUsuario.ADMIN) {
            throw new UnsupportedOperationException(
                    "No tienes permisos para eliminar tickets"
            );
        }
        // validamos existencia
        buscarTicketId(idTicket);
        ticketRepository.eliminarLogico(idTicket);
        return true;
    }

    // Cambiar el estado del ticket setEstadoActual ABIERTO, EN_PROCESO, FINALIZADO
    public boolean cambiarEstadodeTicket(int idTicket, EstadoOperacional nuevoEstado){
        Ticket ticketEncontrado = buscarTicketId(idTicket);
        if (ticketEncontrado == null){
            throw new TicketNotFoundException(idTicket);
        }
        ticketEncontrado.setEstadoOperacionalActual(nuevoEstado);
        ticketRepository.actualizar(ticketEncontrado);
        return true;
    }

    // Cambiar la prioridad del ticket setPrioridadActual ALTA, MEDIA, BAJA
    public boolean cambiarPrioridadTicket(int idTicket, Prioridad prioridad){
        Ticket ticketEncontrado = buscarTicketId(idTicket);
        if (ticketEncontrado == null){
            throw new TicketNotFoundException(idTicket);
        }
        ticketEncontrado.setPrioridadActual(prioridad);
        ticketRepository.actualizar(ticketEncontrado);
        return true;
    }

    // Mostrar estadisticas de los tickets
    public Map<String, Integer> obtenerEstadisticas(){

        List<Ticket> tickets = ticketRepository.listarTickets();

        int abierto = 0;
        int proceso = 0;
        int cerrado = 0;
        int cancelado = 0;

        for (Ticket ticket : tickets){
            switch (ticket.getEstadoOperacionalActual()){
                case ABIERTO -> abierto++;
                case EN_PROCESO -> proceso++;
                case CERRADO -> cerrado++;
                case CANCELADO -> cancelado++;
            }
        }

        Map<String, Integer> stats = new HashMap<>();
        stats.put("TOTAL", tickets.size());
        stats.put("ABIERTOS", abierto);
        stats.put("EN_PROCESO",proceso);
        stats.put("CERRADO", cerrado);
        stats.put("CANCELADOS", cancelado);

        return stats;
    }
}
