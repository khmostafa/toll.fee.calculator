package com.city.traffic.toll.fee.calculator.user.model.payload.request;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EmployeePayload implements Serializable {
    private String name;
    private String email;
    private String profileImage;
    private EmployeeRole role;
}
