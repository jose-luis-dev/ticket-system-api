package com.ticketSystem.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTicketRequest {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    public String getTitulo(){
        return titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
