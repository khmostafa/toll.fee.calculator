package com.city.traffic.toll.fee.calculator.toll.model.payload.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TollPayload {
    private String vehicleNo;
    private String type;
    private LocalDateTime date;
}
