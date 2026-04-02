package com.ticketSystem.controller.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success", "error", "message" })
public class ErrorResponse {

    private boolean success;
    private String error;
    private String message;

    public ErrorResponse(String error, String message){
        this.success = false;
        this.error = error;
        this.message = message;
    }

    public boolean isSuccess() {return  success; }
    public String getError() { return error; }
    public String getMessage() { return message; }
}
