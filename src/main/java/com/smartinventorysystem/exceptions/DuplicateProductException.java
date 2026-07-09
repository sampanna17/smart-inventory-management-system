package com.smartinventorysystem.exceptions;

public class DuplicateProductException extends DuplicateResourceException  {

    public DuplicateProductException(String message) {
        super(message);
    }
}