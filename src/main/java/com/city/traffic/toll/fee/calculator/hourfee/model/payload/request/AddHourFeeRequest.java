package com.city.traffic.toll.fee.calculator.hourfee.model.payload.request;

import lombok.Data;
import java.time.LocalTime;

@Data
public class AddHourFeeRequest {
    private LocalTime startTime;
    private LocalTime endTime;
    private Double value;
}
