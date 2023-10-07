package com.city.traffic.toll.fee.calculator;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.EmployeePayload;

public class StaticEntityProvider {
    public static EmployeeEntity createEmployee(String name, String email, String profileImage, EmployeeRole role){
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(name);
        employee.setEmail(email);
        employee.setRole(role);
        employee.setProfileImage(profileImage);
        return employee;
    }
}
