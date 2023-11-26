package com.BikkadIT.electronic.store.exceptions;

public class ResourceNotFound extends RuntimeException{

    String message;

    public ResourceNotFound(String message) {
        super(message);
        this.message = message;
    }
}