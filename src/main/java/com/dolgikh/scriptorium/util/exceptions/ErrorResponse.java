package com.dolgikh.scriptorium.util.exceptions;

import java.util.Date;

public record ErrorResponse(String message, Date timestamp) {
    public ErrorResponse(String message) {
        this(message, new Date());
    }
}
