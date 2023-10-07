package com.city.traffic.toll.fee.calculator.freevehicle.model.payload.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class FreeVehicleResponse implements Serializable {
    private Long id;
    private String type;
}
