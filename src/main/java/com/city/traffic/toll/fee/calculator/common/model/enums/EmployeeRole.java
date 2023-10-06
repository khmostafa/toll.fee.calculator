package com.city.traffic.toll.fee.calculator.common.model.enums;

public enum EmployeeRole {
    SUPER_ADMIN(5L),
    ADMIN(3L),
    EMPLOYEE(1L),
    JUST_USER(0L);



    private final long longValue;

    private EmployeeRole(long longValue) {
        this.longValue = longValue;
    }

    public long getLongValue() {
        return longValue;
    }
}
