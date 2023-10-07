package com.city.traffic.toll.fee.calculator;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.EmployeePayload;

public class StaticDtoProvider {
    public static EmployeePayload createEmployeePayload(String name, String email, String profileImage, EmployeeRole role){
        return EmployeePayload.builder()
                .name(name)
                .email(email)
                .profileImage(profileImage)
                .role(role)
                .build();
    }
}
