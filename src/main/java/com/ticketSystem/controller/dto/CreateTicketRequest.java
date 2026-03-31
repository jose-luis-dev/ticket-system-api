package com.ticketSystem.controller.dto;

public class CreateTicketRequest {
    private String titulo;
    private String descripcion;

    public String getTitulo(){
        return titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
