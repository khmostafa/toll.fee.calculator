package com.city.traffic.toll.fee.calculator.toll.model.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FeeResponse {
    private String vehicleNo;
    private String type;
    private LocalDate date;
    private Long fee;
}
