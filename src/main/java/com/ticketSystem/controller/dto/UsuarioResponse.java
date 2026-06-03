package com.ticketSystem.controller.dto;

import com.ticketSystem.enums.EstadoRegistro;
import com.ticketSystem.enums.RolUsuario;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private Long id;
    private String username;
    private String nombre;
    private String email;
    private RolUsuario rol;
    private EstadoRegistro estado;
    private LocalDateTime createdAt;
    private String createdBy;

    public UsuarioResponse(Long id, String username, String nombre,
                           String email, RolUsuario rol, EstadoRegistro estado,
                           LocalDateTime createdAt, String createdBy) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public RolUsuario getRol() {
        return rol;
    }
    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public EstadoRegistro getEstado() {
        return estado;
    }
    public void setEstado(EstadoRegistro estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
