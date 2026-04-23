package com.ticketSystem.repository;

import com.ticketSystem.enums.EstadoRegistro;
import com.ticketSystem.enums.RolUsuario;
import com.ticketSystem.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryJdbc implements IUsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper
    //Convierte una fila de la tabla Usuarios - objeto Usuario
    // Se ejecuta por cada fila que retorna la query
    private final RowMapper<Usuario> usuarioRowMapper = ((rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setRol(RolUsuario.valueOf(rs.getString("rol")));
        u.setEstado(EstadoRegistro.valueOf(rs.getString("estado")));
        u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        u.setCreatedBy(rs.getString("created_by"));
        return u;
    });

    // findByUsername
    @Override
    public Optional<Usuario> findByUsername(String username){
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        List<Usuario> result = jdbcTemplate.query(sql, usuarioRowMapper, username);
        // si no existe
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    // findByEmail
    @Override
    public Optional<Usuario> findByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        List<Usuario> result = jdbcTemplate.query(sql, usuarioRowMapper, email);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    // existsByUsername
    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT * COUNT(*) FROM usuarios WHERE username = ?";
        Integer count =jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    // existsByEmail
    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT * COUNT(*) FROM usuarios WHERE email = ?";
        Integer count =jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    // save
    @Override
    public Usuario save(Usuario usuario){
        String sql = """
                INSERT INTO usuarios (username, password, nombre, email, rol, estado, created_at, created_by) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        // keyHolder captura el Id autogenerado por MySQL
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword()); // viene encriptado del UseCase
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getRol().name());
            ps.setString(6, usuario.getEstado().name());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, usuario.getCreatedBy());
            return ps;
        }, keyHolder);


        // Asigna Id generada y lo retorna completo
        usuario.setId(keyHolder.getKey().longValue());
        return usuario;
    }


}
