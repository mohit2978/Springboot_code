package com.example.exceptionhandling.exception;

public class ControllerSpecificException extends RuntimeException {

    public ControllerSpecificException(String message) {
        super(message);
    }
}
