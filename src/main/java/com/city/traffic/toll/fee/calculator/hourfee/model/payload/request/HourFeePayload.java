package com.city.traffic.toll.fee.calculator.hourfee.model.payload.request;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class HourFeePayload implements Serializable {
    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull(message = ErrorKeys.HOUR_FEE_START_TIME_MUST_NOT_BE_NULL)
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull(message = ErrorKeys.HOUR_FEE_END_TIME_MUST_NOT_BE_NULL)
    private LocalTime endTime;

    @NotNull(message = ErrorKeys.HOUR_FEE_VALUE_TIME_MUST_NOT_BE_NULL)
    @Min(value = 8, message = ErrorKeys.HOUR_FEE_VALUE_TIME_MUST_NOT_BE_NULL)
    @Max(value = 18, message = ErrorKeys.HOUR_FEE_VALUE_TIME_MUST_NOT_BE_NULL)
    private Long value;
}
