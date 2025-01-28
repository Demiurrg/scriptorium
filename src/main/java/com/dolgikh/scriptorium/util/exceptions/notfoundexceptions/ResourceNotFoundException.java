package com.dolgikh.scriptorium.util.exceptions.notfoundexceptions;

public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, int id) {
        super(resourceName + " with id " + id + " was not found");
    }

    public ResourceNotFoundException(String resourceName, String name) {
        super(resourceName + " with name " + name + " was not found");
    }
}
