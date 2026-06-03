package com.ticketSystem.repository;

import com.ticketSystem.model.Usuario;

import java.util.Optional;

public interface IUsuarioRepository {

    // Busca por username - lo usa CustomUserDetailsService en cada requiest
    Optional<Usuario> findByUsername (String username);

    // Busca por email - validación al registrar
    Optional<Usuario> findByEmail(String email);

    // Guardar nuevo usuario - lo usa RegisterUserUseCase
    Usuario save(Usuario usuario);

    // Verifica duplicados antes de insertar - Esto evita excepciones de DB
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
