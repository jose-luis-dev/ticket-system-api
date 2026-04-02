package com.ticketSystem.controller.dto;

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
