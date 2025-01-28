package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class ReviewNotFoundException extends ResourceNotFoundException {
    public ReviewNotFoundException(int id) {
        super("Review", id);
    }
}
