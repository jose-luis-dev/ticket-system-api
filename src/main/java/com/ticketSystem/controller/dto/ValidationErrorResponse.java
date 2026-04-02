package com.ticketSystem.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Map;

@JsonPropertyOrder({"success", "error", "message" })
public class ValidationErrorResponse {

    private boolean success;
    private String error;
    private String message;
    private Map<String, String> fields;

    public ValidationErrorResponse(String message, Map<String, String> fields) {
        this.success = false;
        this.error = "VALIDATION_ERROR";
        this.message = message;
        this.fields = fields;
    }

    public boolean isSuccess() { return success; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public Map<String, String> getFields() { return fields; }

}
