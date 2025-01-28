package com.dolgikh.scriptorium.util.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private final String message;
    private final Date timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = new Date();
    }
}

