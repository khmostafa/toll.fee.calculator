package com.city.traffic.toll.fee.calculator.toll.model.payload.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class TollPayload {
    private String vehicleNo;
    private String type;
    private LocalDate day;
    private LocalTime time;
}
