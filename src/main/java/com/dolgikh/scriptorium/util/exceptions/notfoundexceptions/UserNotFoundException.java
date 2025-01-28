package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(int id) {
        super("User", id);
    }

    public UserNotFoundException(String name) {
        super("User", name);
    }
}
