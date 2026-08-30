package com.example.exceptionhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED, reason = "Payment is required to access this resource")
public class AnnotatedPaymentException extends RuntimeException {

    public AnnotatedPaymentException(String message) {
        super(message);
    }
}
