package com.ticketSystem.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ticketSystem.enums.EstadoOperacional;
import com.ticketSystem.enums.Prioridad;

@JsonPropertyOrder({"id", "titulo", "descripcion", "estadoOperacional", "prioridad", "fechaCreacion"})
public class TicketResponse {

    private int id;
    private String titulo;
    private String descripcion;
    private EstadoOperacional estadoOperacional;
    private Prioridad prioridad;


    public TicketResponse(int id, String titulo, String descripcion,
                          EstadoOperacional estadoOperacional, Prioridad prioridad){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estadoOperacional = estadoOperacional;
        this.prioridad = prioridad;
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

    public EstadoOperacional getEstadoOperacional() {
        return estadoOperacional;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }
}
