package com.city.traffic.toll.fee.calculator.freedays.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.AddFreeDayRequest;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.freedays.service.FreeDayService;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.AddEmployeeRequest;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/free-day")
@RequiredArgsConstructor
public class FreeDayApi {
    private final FreeDayService freeDayService;

    @PostMapping
    public ApiResponse<FreeDayResponse> add(@RequestBody AddFreeDayRequest addFreeDayRequest){
        return freeDayService.add(addFreeDayRequest);
    }

    @DeleteMapping("/{freeDayId}")
    public ApiResponse<FreeDayResponse> delete(@PathVariable("freeDayId") Long id){
        return freeDayService.delete(id);
    }
    @GetMapping
    public ApiResponse<PaginationDto<List<FreeDayResponse>>> list(@RequestParam int offset, @RequestParam int limit){
        return freeDayService.list(offset, limit);
    }
}
