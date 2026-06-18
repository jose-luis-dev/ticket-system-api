package com.ticketSystem.repository;

import com.ticketSystem.enums.TipoAsignacion;
import com.ticketSystem.model.Asignacion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class AsignacionRepositoryJdbc  implements IAsignacionRepository {

    private final JdbcTemplate jdbc;

    public AsignacionRepositoryJdbc(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    // -- RowMapper

    private final RowMapper<Asignacion> asignacionMapper = (rs, rowNum) -> {
        Asignacion a = new Asignacion();
        a.setId(rs.getLong("id"));
        a.setTicketId(rs.getInt("ticket_id"));
        a.setAgenteId(rs.getLong("agente_id"));

        long asignadoPor = rs.getLong("asignado_por");
        a.setAsignadoPor(rs.wasNull() ? null : asignadoPor);

        a.setTipo(TipoAsignacion.valueOf(rs.getString("tipo")));
        a.setMotivo(rs.getString("motivo"));
        a.setComentario(rs.getString("comentario"));

        Timestamp ts = rs.getTimestamp("fecha_asignacion");
        if (ts != null) a.setFechaAsignacion(ts.toLocalDateTime());

        return a;
    };

    // --- save

    @Override
    public Asignacion save(Asignacion asignacion) {
        String sql = """
                INSERT INTO asignaciones
                    (ticket_id, agente_id, asignado_por, tipo, motivo, comentario)
                VALUES (?,?,?,?,?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, asignacion.getTicketId());
            ps.setLong(2, asignacion.getAgenteId());

            if (asignacion.getAsignadoPor() != null) {
                ps.setLong(3, asignacion.getAsignadoPor());
            }else {
                ps.setNull(3, Types.BIGINT);
            }

            ps.setString(4, asignacion.getTipo().name());
            ps.setString(5, asignacion.getMotivo());
            ps.setString(6, asignacion.getComentario());
            return ps;
        }, keyHolder);

        asignacion.setId(keyHolder.getKey().longValue());
        return asignacion;
    }

    // findCurrentByTicketId

    @Override
    public Optional<Asignacion> findCurrentByTicketId(Integer ticketId) {
        String sql = """
                SELECT * FROM asignaciones
                WHERE ticket_id = ?
                ORDER BY fecha_asignacion DESC
                LIMIT 1
                """;

        List<Asignacion> results = jdbc.query(sql, asignacionMapper, ticketId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    // findHistoryByTicketId

    @Override
    public List<Asignacion> findHistoryByTicketId(Integer ticketId) {
        String sql = """
                SELET * FROM asignaciones
                WHERE ticket_id = ?
                ORDER BY fecha_asignacion ASC
                """;

        return jdbc.query(sql, asignacionMapper, ticketId);
    }

    // findAgentWithLeastTickets

    @Override
    public Optional<Long> findAgentWithLeastTickets() {
        String sql = """
                SELECT u.id, COUNT(a.id)  AS total_activos
                FROM usuarios u
                LEFT JOIN asignaciones a
                    ON u.id = a.agente_id
                    AND a.ticket_id IN (
                        SELECT id FROM tickets
                        WHERE estado IN ('ABIERTO', 'EN_PROCESO')
                    )
                WHERE u.rol = 'USER'
                AND u.estado = 'ACTIVO'
                GROUP BY u.id
                ORDER BY total_activos ASC
                LIMIT 1
                """;

        List<Long> results = jdbc.query(sql,(rs, rowNum) -> rs.getLong("id"));
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));

    }


}
