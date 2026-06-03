package com.ticketSystem.controller.dto;

import com.ticketSystem.model.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getEstado(),
                usuario.getCreatedAt(),
                usuario.getCreatedBy()
        );
    }

}
