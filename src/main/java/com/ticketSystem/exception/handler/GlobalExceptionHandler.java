package com.ticketSystem.exception.handler;

import com.ticketSystem.controller.dto.ErrorResponse;
import com.ticketSystem.controller.dto.ValidationErrorResponse;
import com.ticketSystem.exception.*;
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

    // 400 - Datos de entrada inválidos
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

    // 400 - Argumento inválido
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex){
        return Map.of(
                "error", "BAD_REQUEST",
                "message", ex.getMessage()
        );
    }

    // 404 - Recursos no encontrado
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(TicketNotFoundException ex) {
        return new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage()
        );
    }

    // 403 - Sin permisos
    @ExceptionHandler(UnauthorizedOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleUnauthorized(UnauthorizedOperationException ex) {
        return new ErrorResponse(
                "FORBIDDEN",
                ex.getMessage()
        );
    }

    // 403 - Sin permisos (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {

        ErrorResponse error = new ErrorResponse(
                "Access denied",
                "FORBIDDEN"
        );

        return ResponseEntity.status(403).body(error);
    }

    // 409 - Operación bloqueada por estado
    @ExceptionHandler(InvalidTransicionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidTransicion(InvalidTransicionException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "INVALID_TRANSITION",
                        ex.getMessage()
                ));
    }

    // 409 - Operación bloqueada por estado
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperation(InvalidOperationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "INVALID_TRANSITION",
                        ex.getMessage()
                ));
    }

    // 500 - Error de base de datos
    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleDatabase(DatabaseException ex){
        return  Map.of(
                "error", "DATABASE_ERROR",
                "message", ex.getMessage()
        );
    }

    // 500 - Error general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>  handleGeneral(Exception ex){

        ErrorResponse error = new ErrorResponse(
                "Internal server error",
                "INTERNAL_ERROR"
        );
        return ResponseEntity.status(500).body(error);

    }
}
