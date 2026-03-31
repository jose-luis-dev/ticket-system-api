package com.ticketSystem.exception.handler;


import com.ticketSystem.exception.DatabaseException;
import com.ticketSystem.exception.TicketNotFoundException;
import com.ticketSystem.exception.UnauthorizedOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(TicketNotFoundException ex) {
        return Map.of(
                "error", "NOT_FOUND",
                "message", ex.getMessage()
        );
    }

    // 403
    @ExceptionHandler(UnauthorizedOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleUnauthorized(UnauthorizedOperationException ex){
        return Map.of(
                "error", "FORBIDDEN",
                "message", ex.getMessage()
        );
    }

    // 500
    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleDatabase(DatabaseException ex){
        return  Map.of(
                "error", "DATABASE_ERROR",
                "message", ex.getMessage()
        );
    }

    // General
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneral(Exception ex){
        return Map.of(
                "error", "INTERNAL_ERROR",
                "message", ex.getMessage()
        );
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex){
        return Map.of(
                "error", "BAD_REQUEST",
                "message", ex.getMessage()
        );
    }



}
