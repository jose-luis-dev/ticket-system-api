package com.ticketSystem.repository;

import com.ticketSystem.enums.EstadoOperacional;
import com.ticketSystem.enums.EstadoRegistro;
import com.ticketSystem.enums.Prioridad;
import com.ticketSystem.exception.DatabaseException;
import com.ticketSystem.model.Ticket;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepositoryJdbc implements ITicketRepository {

    private final DataSource dataSource;

    public TicketRepositoryJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Ticket> listarTickets() {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT * FROM tickets WHERE estado_registro = 'ACTIVO'";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
                Ticket ticket = new Ticket(
                        rs.getInt("Id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                );

                ticket.setEstadoOperacionalActual(
                        EstadoOperacional.valueOf(rs.getString("estado_operacional"))
                );

                ticket.setPrioridadActual(
                        Prioridad.valueOf(rs.getString("prioridad"))
                );

                ticket.setEstadoOperacionalTicket(
                        EstadoRegistro.valueOf(rs.getString("estado_registro"))
                );

                tickets.add(ticket);
            }
        }catch (SQLException e){
            throw new DatabaseException("Error listado tickets ", e);
        }
        return tickets;
    }

    @Override
    public Ticket buscarPorId(int id) {
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                );

                ticket.setEstadoOperacionalActual(
                        EstadoOperacional.valueOf(rs.getString("estado_operacional"))
                );

                ticket.setPrioridadActual(
                        Prioridad.valueOf(rs.getString("prioridad"))
                );

                ticket.setEstadoOperacionalTicket(
                        EstadoRegistro.valueOf(rs.getString("estado_registro"))
                );

                return ticket;
            }

        }catch (SQLException e){
            throw new DatabaseException("Error buscando ticket", e);
        }
        return null;

    }

    @Override
    public void guardar(Ticket ticket){

        String sql = """
                INSERT INTO tickets
                (titulo, descripcion, estado_operacional, prioridad, estado_registro)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ticket.getTitulo());
            ps.setString(2,ticket.getDescripcion());
            ps.setString(3,ticket.getEstadoOperacionalActual().name());
            ps.setString(4, ticket.getPrioridadActual().name());
            ps.setString(5, ticket.getEstadoOperacionalTicket().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ticket.setIdTicket(rs.getInt(1));
            }

        } catch (SQLException e){
            throw new DatabaseException("Error guardando ticket: ", e);
        }
    }

    @Override
    public void actualizar(Ticket ticket) {

        String sql = """
                UPDATE tickets
                SET estado_operacional = ?,
                    prioridad = ?,
                    estado_registro = ?
                WHERE id = ?
                """;
        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ticket.getEstadoOperacionalActual().name());
            ps.setString(2, ticket.getPrioridadActual().name());
            ps.setString(3, ticket.getEstadoOperacionalTicket().name());
            ps.setInt(4, ticket.getIdTicket());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0){
                throw new RuntimeException("No se encontró ticket con ID: " + ticket.getIdTicket());
            }
        }catch (SQLException e){
            throw new DatabaseException("Error actualizado ticket", e);
        }

    }

    @Override
    public void eliminarLogico(int id) {

        String sql = """
                UPDATE tickets
                SET estado_registro = ?
                WHERE id = ?
                """;
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, EstadoRegistro.INACTIVO.name());
            ps.setInt(2,id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0){
                throw new RuntimeException("No se encontró ticket con Id: " + id);
            }
        }catch (SQLException e){
            throw new RuntimeException("Error eliminado ticket", e);
        }

    }

}
