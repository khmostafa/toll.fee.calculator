package com.city.traffic.toll.fee.calculator.common.exception;import lombok.Getter;import org.springframework.http.HttpStatus;import java.util.Map;@Getterpublic abstract class BusinessException extends RuntimeException {    private final String message;    private final Throwable cause;    private final HttpStatus status;    private final Map<String, String> details;    protected BusinessException(HttpStatus status) {        this.status = status;        this.message = null;        this.details = null;        this.cause = null;    }    protected BusinessException(String message, HttpStatus status) {        super(message);        this.message = message;        this.status = status;        this.cause = null;        this.details = null;    }    protected BusinessException(String message, HttpStatus status, Map<String, String> details) {        super(message);        this.message = message;        this.status = status;        this.details = details;        this.cause = null;    }    protected BusinessException(String message, Throwable cause, HttpStatus status) {        super(message, cause);        this.message = message;        this.status = status;        this.cause = cause;        this.details = null;    }}