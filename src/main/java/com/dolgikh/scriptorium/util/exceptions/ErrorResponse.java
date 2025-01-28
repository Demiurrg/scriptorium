package com.dolgikh.scriptorium.util.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorResponse {
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String bookNotFoundMessage(int id) {
        return "Book with id " + id + " was not found";
    }

    public static String userNotFoundMessage(int id) {
        return "Book with id " + id + " was not found";
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

