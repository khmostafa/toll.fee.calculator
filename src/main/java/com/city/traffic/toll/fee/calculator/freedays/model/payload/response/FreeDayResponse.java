package com.city.traffic.toll.fee.calculator.freedays.model.payload.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class FreeDayResponse {
    private Long id;
    private String name;
    private LocalDate freeDay;
}
