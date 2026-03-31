package com.ticketSystem.model;

import com.ticketSystem.enums.RolUsuario;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String password;
    private RolUsuario rol;


    public Usuario(int idUsuario, String nombre, String password, RolUsuario rol){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public String getNombre() { return this.nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public RolUsuario getRol() { return this.rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    public String getPassword() { return this.password;}
    public void setPassword(String password) { this.password = password; }
}
