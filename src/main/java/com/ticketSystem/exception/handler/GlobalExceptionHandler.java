package com.ticketSystem.exception.handler;


import com.ticketSystem.controller.dto.ErrorResponse;
import com.ticketSystem.controller.dto.ValidationErrorResponse;
import com.ticketSystem.exception.DatabaseException;
import com.ticketSystem.exception.TicketNotFoundException;
import com.ticketSystem.exception.UnauthorizedOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ValidationErrorResponse(
                "Datos inválidos",
                errors
        );
    }


    // Manejo de 404
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(TicketNotFoundException ex) {
        return new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage()
        );
    }

    // Manejo de 403
    @ExceptionHandler(UnauthorizedOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleUnauthorized(UnauthorizedOperationException ex) {
        return new ErrorResponse(
                "FORBIDDEN",
                ex.getMessage()
        );
    }

    // Manejo de 500
    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleDatabase(DatabaseException ex){
        return  Map.of(
                "error", "DATABASE_ERROR",
                "message", ex.getMessage()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {

        ErrorResponse error = new ErrorResponse(
                "Access denied",
                "FORBIDDEN"
        );

        return ResponseEntity.status(403).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex){
        return Map.of(
                "error", "BAD_REQUEST",
                "message", ex.getMessage()
        );
    }


    // Exception general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>  handleGeneral(Exception ex){

        ErrorResponse error = new ErrorResponse(
                "Internal server error",
                "INTERNAL_ERROR"
        );
        return ResponseEntity.status(500).body(error);

    }
}
