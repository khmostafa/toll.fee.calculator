package com.city.traffic.toll.fee.calculator.user.model.payload.request;

import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddEmployeeRequest implements Serializable {
    private String name;
    private String email;
    private String profileImage;
    private EmployeeRole role;
}
