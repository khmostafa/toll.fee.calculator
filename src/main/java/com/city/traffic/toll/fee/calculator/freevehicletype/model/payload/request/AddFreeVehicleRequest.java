package com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddFreeVehicleRequest {
    private String type;
}
