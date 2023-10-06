package com.city.traffic.toll.fee.calculator.toll.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.response.HourFeeResponse;
import com.city.traffic.toll.fee.calculator.toll.model.payload.request.TollPayload;
import com.city.traffic.toll.fee.calculator.toll.model.payload.response.FeeResponse;
import com.city.traffic.toll.fee.calculator.toll.model.payload.response.TollResponse;
import com.city.traffic.toll.fee.calculator.toll.service.TollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/toll")
@RequiredArgsConstructor
public class TollApi {
    private final TollService tollService;

    @PostMapping
    public ApiResponse<TollResponse> add(@RequestHeader("email") String email, @RequestBody TollPayload tollPayload){
        return tollService.add(email, tollPayload);
    }

    @GetMapping("/fee/{vehicleNo}")
    public ApiResponse<PaginationDto<List<FeeResponse>>> getVehicleFee(@RequestHeader("email") String email, @PathVariable("vehicleNo") String vehicleNo, @RequestParam int offset, @RequestParam int limit){
        return tollService.tollFee(email, vehicleNo, offset, limit);
    }

    @GetMapping("/history/{vehicleNo}")
    public ApiResponse<PaginationDto<List<TollResponse>>> getVehicleTollHistory(@RequestHeader("email") String email, @PathVariable("vehicleNo") String vehicleNo, @RequestParam int offset, @RequestParam int limit){
        return tollService.listTolls(email, vehicleNo, offset, limit);
    }

}
