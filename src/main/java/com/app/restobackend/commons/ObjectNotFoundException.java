package com.app.restobackend.commons;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException() {
        super("Object not found");
    }
}
