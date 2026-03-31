package com.ticketSystem.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException( String message, Throwable cause){
        super(message, cause);
    }
}
