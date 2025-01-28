package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class BookNotFoundException extends ResourceNotFoundException {
    public BookNotFoundException(int id) {
        super("Book", id);
    }
}
