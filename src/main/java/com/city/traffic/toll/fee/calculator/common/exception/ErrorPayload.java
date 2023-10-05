package com.city.traffic.toll.fee.calculator.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorPayload {
    private String enMessage;
    private String arMessage;
    private String type;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private Map<String, Object> details;
    private String detailsAsString;
    private String code;
}