package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class GenreNotFoundException extends ResourceNotFoundException {
    public GenreNotFoundException(long id) {
        super("Genre", id);
    }
}
