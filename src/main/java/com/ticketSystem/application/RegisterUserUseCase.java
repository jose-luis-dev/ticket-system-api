package com.ticketSystem.application;

import com.ticketSystem.controller.dto.RegisterUserRequest;
import com.ticketSystem.enums.EstadoRegistro;
import com.ticketSystem.exception.DatabaseException;
import com.ticketSystem.model.Usuario;
import com.ticketSystem.repository.IUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserUseCase {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(IUsuarioRepository usuarioRepository,
                               PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario execute(RegisterUserRequest request, String createBy) {
        // Validar que usename no exista
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new DatabaseException(
                    "El username '" + request.getUsername() + "' ya está en uso"
            );
        }

        // Validar email no exista
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DatabaseException(
                    "El email '" + request.getEmail() + "' ya está registrado"
            );
        }

        // Se contruye el usuario - password encripta
        Usuario nuevo = new Usuario();
        nuevo.setUsername(request.getUsername());
        nuevo.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevo.setNombre(request.getNombre());
        nuevo.setEmail(request.getEmail());
        nuevo.setRol(request.getRol());
        nuevo.setEstado(EstadoRegistro.ACTIVO);
        nuevo.setCreatedBy(createBy);

        // Persistir y retornar
        return usuarioRepository.save(nuevo);

    }

}
