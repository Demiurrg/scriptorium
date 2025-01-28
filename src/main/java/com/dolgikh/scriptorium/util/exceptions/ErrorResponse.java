package com.dolgikh.scriptorium.util.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorResponse {
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

