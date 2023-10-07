package com.city.traffic.toll.fee.calculator.hourfee.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.response.HourFeeResponse;
import com.city.traffic.toll.fee.calculator.hourfee.service.HourFeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hour-fee")
@RequiredArgsConstructor
@Validated
public class HourFeeApi {
    private final HourFeeService hourFeeService;

    @PostMapping
    public ApiResponse<HourFeeResponse> add(@RequestHeader("email") String email, @Valid @RequestBody HourFeePayload hourFeePayload){
        return hourFeeService.add(email, hourFeePayload);
    }

    @PutMapping("/{hourFeeId}")
    public ApiResponse<HourFeeResponse> update(@RequestHeader("email") String email, @Valid @PathVariable("hourFeeId") Long id, @RequestBody HourFeePayload hourFeePayload){
        return hourFeeService.update(email, id, hourFeePayload);
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
