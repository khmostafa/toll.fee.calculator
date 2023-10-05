package com.city.traffic.toll.fee.calculator.user.model.payload.response;

import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeResponse  implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String profileImage;
    private EmployeeRole role;
}
