package com.city.traffic.toll.fee.calculator.toll.tollcalculator;

import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.toll.model.entity.TollEntity;
import com.city.traffic.toll.fee.calculator.toll.model.payload.response.FeeResponse;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TollCalculator {

    
    public PaginationDto<List<FeeResponse>> getTotalFee(int offset, int limit, List<TollEntity> tolls, List<String> freeTypes, List<LocalDate> freeDays, List<HourFeeEntity> fees){
        String type = tolls.stream().findFirst().map(TollEntity::getType).orElse(null);
        String vehicleNo = tolls.stream().findFirst().map(TollEntity::getVehicleNo).orElse(null);
        if (isTollFreeVehicle(type, freeTypes)){return getPage(offset, limit,  new ArrayList<>());}
        
        Map<LocalDate, List<List<LocalDateTime>>> groupedDays = tolls
                .stream()
                .map(TollEntity::getDate)
                .filter(date -> !isTollFreeDate(date, freeDays))
                .collect(Collectors.groupingBy(
                        LocalDateTime::toLocalDate,
                        Collectors.collectingAndThen(Collectors.toList(), this::groupByHours)
                ));

        Map<LocalDate, Long> totalFee = groupedDays.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,                // Key remains the same
                        entry -> getTollFeePerDay(entry.getValue(), fees)   // Apply a function to the value (e.g., doubling it)
                ));

        List<FeeResponse> feeResponses = totalFee.entrySet().stream().map(
                e -> FeeResponse.builder()
                        .vehicleNo(vehicleNo)
                        .type(type)
                        .date(e.getKey())
                        .fee(e.getValue()).build()
        ).toList();

        return getPage(offset, limit, feeResponses);

    }

    private boolean isTollFreeVehicle(String type, List<String> freeTypes) {
        return freeTypes.stream().filter(Objects::nonNull).anyMatch(ft -> ft.equalsIgnoreCase(type));
    }

    private PaginationDto<List<FeeResponse>> getPage(int offset, int limit, List<FeeResponse> originalList){
        if (offset < 0 || limit <= 0 || offset >= originalList.size()) {
            return PaginationDto.<List<FeeResponse>>builder()
                            .data(new ArrayList<>())
                            .totalElements(0L)
                            .totalPages(0).build();
        }
        List<FeeResponse> data = originalList.subList(offset, Math.min(offset + limit, originalList.size()));
        return PaginationDto.<List<FeeResponse>>builder()
                .data(data)
                .totalElements((long) data.size())
                .totalPages((int) Math.ceil((double) originalList.size() / limit)).build();
    }

    private Long getTollFeeForOneTime(LocalDateTime tollTime, List<HourFeeEntity> hourFees) {
        LocalTime time = tollTime.toLocalTime();
        return hourFees.stream().filter(h -> time.isAfter(h.getStartTime()) && time.isBefore(h.getEndTime()))
                .findFirst().map(HourFeeEntity::getValue).orElse(0L);
    }
    private Long getTollFeePerHour(List<LocalDateTime> tollTimes, List<HourFeeEntity> hourFees) {
        return tollTimes.stream().map(tollDate -> getTollFeeForOneTime(tollDate, hourFees)).max(Long::compare).orElse(0L);
    }

    private Long getTollFeePerDay(List<List<LocalDateTime>> tollDates, List<HourFeeEntity> hourFees) {
        long dayTotalFee = tollDates.stream().map(t -> getTollFeePerHour(t, hourFees)).mapToLong(Long::longValue).sum();
        return dayTotalFee > 60 ? 60 : dayTotalFee;
    }
    private Boolean isTollFreeDate(LocalDateTime date, List<LocalDate> freeDays) {
        return freeDays.stream().filter(Objects::nonNull).anyMatch(fd -> fd.equals(date.toLocalDate()));
    }

    private List<List<LocalDateTime >> groupByHours(List<LocalDateTime> localDateTimes){
        List<LocalDateTime> sortedList = localDateTimes.stream().sorted().toList();

        List<List<LocalDateTime>> result = new ArrayList<>();
        List<LocalDateTime> currentGroup = new ArrayList<>();


        for (LocalDateTime dateTime : sortedList) {
            if (currentGroup.isEmpty() || currentGroup.get(0).plusHours(1).isAfter(dateTime)) {
                currentGroup.add(dateTime);
            } else {
                result.add(currentGroup);
                currentGroup.clear();
                currentGroup.add(dateTime);
            }
        }

        // Add the last group to the result
        if (!currentGroup.isEmpty()) {
            result.add(new ArrayList<>(currentGroup));
        }
        return result;
    }
}
