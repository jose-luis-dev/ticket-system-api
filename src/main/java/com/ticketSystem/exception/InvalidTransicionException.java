package com.ticketSystem.exception;

import com.ticketSystem.enums.EstadoOperacional;

public class InvalidTransicionException  extends RuntimeException{

    private final EstadoOperacional estadoActual;
    private final EstadoOperacional estadoSolicitado;

    public InvalidTransicionException(EstadoOperacional actual, EstadoOperacional solicitado ){
        super(String.format(
                "Transición invalida: no se puede cambiar de '%s' a '%s'", actual, solicitado
        ));
        this.estadoActual = actual;
        this.estadoSolicitado = solicitado;
    }

    public EstadoOperacional getEstadoActual() { return estadoActual; }
    public EstadoOperacional getEstadoSolicitado() { return estadoSolicitado; }

}
