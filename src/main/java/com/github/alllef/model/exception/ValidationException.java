package com.github.alllef.model.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super();
    }
}
