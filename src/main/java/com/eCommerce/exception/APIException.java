package com.eCommerce.exception;

public class APIException extends RuntimeException{
    private final Long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
