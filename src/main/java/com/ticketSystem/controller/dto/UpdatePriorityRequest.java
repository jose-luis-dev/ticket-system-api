package com.ticketSystem.controller.dto;

import com.ticketSystem.enums.Prioridad;
import jakarta.validation.constraints.NotNull;

public class UpdatePriorityRequest {

    @NotNull(message = "La prioridad es obligatoria")
    private Prioridad prioridad;

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
}
