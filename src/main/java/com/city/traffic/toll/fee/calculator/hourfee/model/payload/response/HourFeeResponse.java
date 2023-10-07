package com.city.traffic.toll.fee.calculator.hourfee.model.payload.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
public class HourFeeResponse implements Serializable {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long value;
}
