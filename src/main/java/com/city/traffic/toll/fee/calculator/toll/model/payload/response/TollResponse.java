package com.city.traffic.toll.fee.calculator.toll.model.payload.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TollResponse {
    private Long id;
    private String vehicleNo;
    private String type;
    private LocalDateTime date;
}
