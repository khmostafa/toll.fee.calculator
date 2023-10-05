package com.city.traffic.toll.fee.calculator.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class STCValidationException extends BusinessException {

    public STCValidationException(HttpStatus status) {
        super(status);
    }

    public STCValidationException(String message, HttpStatus status) {
        super(message, status);
    }

    public STCValidationException(String message, HttpStatus status, Map<String, String> details) {
        super(message, status, details);
    }

    public STCValidationException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }
}

