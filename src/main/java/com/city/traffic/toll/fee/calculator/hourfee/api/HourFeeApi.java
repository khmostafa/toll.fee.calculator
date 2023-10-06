package com.city.traffic.toll.fee.calculator.hourfee.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.AddFreeDayRequest;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.AddHourFeeRequest;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.response.HourFeeResponse;
import com.city.traffic.toll.fee.calculator.hourfee.service.HourFeeService;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hour-fee")
@RequiredArgsConstructor
public class HourFeeApi {
    private final HourFeeService hourFeeService;

    @PostMapping
    public ApiResponse<HourFeeResponse> add(@RequestHeader("email") String email, @RequestBody AddHourFeeRequest addHourFeeRequest){
        return hourFeeService.add(email, addHourFeeRequest);
    }

    @PutMapping("/{hourFeeId}")
    public ApiResponse<HourFeeResponse> update(@RequestHeader("email") String email, @PathVariable("hourFeeId") Long id, @RequestBody AddHourFeeRequest addHourFeeRequest){
        return hourFeeService.update(email, id, addHourFeeRequest);
    }

    @DeleteMapping("/{hourFeeId}")
    public ApiResponse<HourFeeResponse> delete(@RequestHeader("email") String email, @PathVariable("hourFeeId") Long id){
        return hourFeeService.delete(email, id);
    }
    @GetMapping
    public ApiResponse<PaginationDto<List<HourFeeResponse>>> list(@RequestHeader("email") String email, @RequestParam int offset, @RequestParam int limit){
        return hourFeeService.list(email, offset, limit);
    }
}
