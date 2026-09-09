package com.smartinventorysystem.exceptions;

public class ImageNotBelongToProductException extends RuntimeException {

    public ImageNotBelongToProductException(String message) {
        super(message);
    }
}
