package com.ticketSystem.exception;

    public class DatabaseException extends RuntimeException {

    // errores tecnicos de DB
    public DatabaseException( String message, Throwable cause){
        super(message, cause);
    }
    // errores de negocio
    public DatabaseException(String message) {
        super(message);
    }
}
