package com.city.traffic.toll.fee.calculator;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.FreeDayPayload;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.request.FreeVehiclePayload;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.EmployeePayload;

import java.time.LocalDate;
import java.time.LocalTime;

public class StaticDtoProvider {
    public static EmployeePayload createEmployeePayload(String name, String email, String profileImage, EmployeeRole role){
        return EmployeePayload.builder()
                .name(name)
                .email(email)
                .profileImage(profileImage)
                .role(role)
                .build();
    }

    public static FreeDayPayload createFreeDayPayLoad(String name, LocalDate freeDay){
        return FreeDayPayload.builder()
                .name(name)
                .freeDay(freeDay)
                .build();
    }

    public static FreeVehiclePayload createFreeVehiclePayLoad(String type){
        return FreeVehiclePayload.builder()
                .type(type)
                .build();
    }

    public static HourFeePayload createHourFeePayLoad(LocalTime s, LocalTime e, Long v){
        return HourFeePayload.builder()
                .startTime(s)
                .endTime(e)
                .value(v)
                .build();
    }
}
