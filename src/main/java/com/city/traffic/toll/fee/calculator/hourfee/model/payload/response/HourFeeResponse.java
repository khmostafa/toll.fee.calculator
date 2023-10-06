package com.city.traffic.toll.fee.calculator.hourfee.model.payload.response;

import lombok.Data;
import java.time.LocalTime;

@Data
public class HourFeeResponse {
    private LocalTime startTime;
    private LocalTime endTime;
    private Double value;
}
