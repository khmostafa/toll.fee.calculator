package com.city.traffic.toll.fee.calculator.freedays.model.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FreeDayPayload {
    private String name;
    private LocalDate freeDay;
}
