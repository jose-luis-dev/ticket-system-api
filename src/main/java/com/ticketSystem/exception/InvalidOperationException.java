package com.ticketSystem.exception;

import com.ticketSystem.enums.EstadoOperacional;

public class InvalidOperationException extends RuntimeException {

    private final EstadoOperacional estadoActual;

    public InvalidOperationException(EstadoOperacional estadoActual){
        super(String.format(
                "No se puede modificar la prioridad de un ticket con estado '%s'", estadoActual
        ));
        this.estadoActual = estadoActual;
    }

    public EstadoOperacional getEstadoActual() {
        return estadoActual;
    }
}
