package com.dolgikh.scriptorium.util;

import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorResponse {
    private String message;
    private long timestamp;

    public ErrorResponse() {}

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static String printFieldErrors(List<FieldError> fieldErrors) {
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : fieldErrors)
            errorMessage
                    .append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");

        return errorMessage.toString();
    }
}

