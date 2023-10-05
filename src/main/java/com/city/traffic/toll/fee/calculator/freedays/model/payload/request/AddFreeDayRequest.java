package com.city.traffic.toll.fee.calculator.freedays.model.payload.request;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddFreeDayRequest {
    private String name;
    private LocalDate freeDay;
}
