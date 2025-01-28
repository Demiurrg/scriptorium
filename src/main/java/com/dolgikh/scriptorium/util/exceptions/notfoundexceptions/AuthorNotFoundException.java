package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class AuthorNotFoundException extends ResourceNotFoundException {
    public AuthorNotFoundException(int id) {
        super("Author", id);
    }
}
