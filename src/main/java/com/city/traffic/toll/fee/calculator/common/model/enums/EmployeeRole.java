package com.city.traffic.toll.fee.calculator.common.model.enums;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import org.springframework.http.HttpStatus;

public enum EmployeeRole {
    SUPER_ADMIN(5L),
    ADMIN(3L),
    EMPLOYEE(1L),
    JUST_USER(0L);


    private final Long longValue;

    private EmployeeRole(Long longValue) {
        this.longValue = longValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public static EmployeeRole fromValue(Long value) {
        for (EmployeeRole role : values()) {
            if (role.getLongValue().equals(value)) {
                return role;
            }
        }
        throw new STCValidationException(ErrorKeys.UN_VALID_STORED_VALUE_FOR_EMPLOYEE_ROLE, HttpStatus.BAD_REQUEST);
    }
}
