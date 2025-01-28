package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class AuthorNotFoundException extends ResourceNotFoundException {
    public AuthorNotFoundException(long id) {
        super("Author", id);
    }
}
