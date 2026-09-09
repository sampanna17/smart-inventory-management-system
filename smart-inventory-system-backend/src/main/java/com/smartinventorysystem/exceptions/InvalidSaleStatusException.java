package com.smartinventorysystem.exceptions;


public class InvalidSaleStatusException extends RuntimeException {
    public InvalidSaleStatusException(String message) {
        super(message);
    }
}