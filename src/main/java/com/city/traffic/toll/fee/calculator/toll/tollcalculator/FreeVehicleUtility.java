package com.city.traffic.toll.fee.calculator.toll.tollcalculator;

import java.util.List;
import java.util.Objects;

public class FreeVehicleUtility {
    private FreeVehicleUtility(){

    }

    public static boolean isTollFreeVehicle(String type, List<String> freeTypes) {
        return freeTypes.stream().filter(Objects::nonNull).anyMatch(ft -> ft.equalsIgnoreCase(type));
    }
}
