package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class BookNotFoundException extends ResourceNotFoundException {
    public BookNotFoundException(long id) {
        super("Book", id);
    }
}
