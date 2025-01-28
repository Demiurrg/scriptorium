package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class ReviewNotFoundException extends ResourceNotFoundException {
    public ReviewNotFoundException(long id) {
        super("Review", id);
    }
}
