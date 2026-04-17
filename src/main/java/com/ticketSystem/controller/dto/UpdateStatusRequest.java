package com.ticketSystem.controller.dto;

import com.ticketSystem.enums.EstadoOperacional;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {

    @NotNull (message = "El estado operacional es obligatorio")
    private EstadoOperacional estadoOperacional;


    public EstadoOperacional getEstadoOperacional () { return estadoOperacional; }
    public void setEstadoOperacional(EstadoOperacional estadoOperacional) {
        this.estadoOperacional = estadoOperacional;
    }

}
