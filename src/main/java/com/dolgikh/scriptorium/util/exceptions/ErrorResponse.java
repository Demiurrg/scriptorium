package com.dolgikh.scriptorium.util.exceptions;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;

@Data
public class ErrorResponse {
    private final String message;
    private final Date timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = new Date();
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

