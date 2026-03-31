package com.ticketSystem.controller.dto;

public class TicketResponse {

    private int id;
    private String titulo;
    private String descripcion;

    public TicketResponse(int id, String titulo, String descripcion){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
