package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class GenreNotFoundException extends ResourceNotFoundException {
    public GenreNotFoundException(int id) {
        super("Genre", id);
    }
}
