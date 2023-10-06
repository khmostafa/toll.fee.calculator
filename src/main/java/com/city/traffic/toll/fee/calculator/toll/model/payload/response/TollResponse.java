package com.city.traffic.toll.fee.calculator.toll.model.payload.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TollResponse {
    private Long id;
    private String vehicleNo;
    private String type;
    private LocalDate day;
    private LocalTime time;
}
