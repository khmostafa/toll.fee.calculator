package com.city.traffic.toll.fee.calculator.toll.tollcalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class FreeDayUtility {
    private FreeDayUtility(){

    }
    public static Boolean isTollFreeDate(LocalDateTime date, List<LocalDate> freeDays) {
        return freeDays.stream().filter(Objects::nonNull).anyMatch(fd -> fd.equals(date.toLocalDate()));
    }
}
