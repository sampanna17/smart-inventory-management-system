package com.smartinventorysystem.exceptions;

public class EmailAlreadyExistedException extends RuntimeException {
    public EmailAlreadyExistedException(String message) {
        super(message);
    }
}