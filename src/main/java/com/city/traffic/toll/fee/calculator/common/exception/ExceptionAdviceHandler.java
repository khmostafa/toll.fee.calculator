package com.city.traffic.toll.fee.calculator.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdviceHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {

        final String simpleName = HttpClientErrorException.class.getSimpleName();
        FieldError error = exception.getBindingResult().getFieldErrors().get(0);

        ErrorPayload errorPayloads = ErrorPayload.builder()
                .enMessage(error.getDefaultMessage())
                .arMessage(error.getDefaultMessage())
                .code(error.getCode())
                .type(simpleName).build();

        return handleErrorResponse(errorPayloads, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<Object> handleException(BusinessException exception) {
        log.info("Error Message: {}", exception.getMessage());
        final String simpleName = exception.getClass().getSimpleName();
        ErrorPayload errorPayloads = ErrorPayload.builder()
                .enMessage(exception.getMessage())
                .arMessage(exception.getMessage())
                .code("" + exception.getStatus().value())
                .type(simpleName).build();

        return handleErrorResponse(errorPayloads, exception.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleUniqueConstraintViolation(DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("unique")) {
            ErrorPayload errorPayloads = ErrorPayload.builder()
                    .enMessage(ErrorKeys.UNIQUE_CONSTRAINS_VIOLATED)
                    .arMessage(ErrorKeys.UNIQUE_CONSTRAINS_VIOLATED)
                    .code("" + HttpStatus.CONFLICT)
                    .type(ex.getClass().getSimpleName()).build();
            return handleErrorResponse(errorPayloads, HttpStatus.CONFLICT);
        }
        ErrorPayload errorPayloads = ErrorPayload.builder()
                .enMessage("Database Error Occurred")
                .arMessage("Database Error Occurred")
                .code("" + HttpStatus.INTERNAL_SERVER_ERROR)
                .type(ex.getClass().getSimpleName()).build();

        return handleErrorResponse(errorPayloads, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> handleErrorResponse(ErrorPayload errors, HttpStatus status) {

        return new ResponseEntity<>(ApiResponse.builder()
                .success(Boolean.FALSE)
                .errors(errors)
                .code(status.value())
                .build(), status);
    }


}
