package com.city.traffic.toll.fee.calculator.freedays.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.AddFreeDayRequest;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.freedays.service.FreeDayService;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/free-day")
@RequiredArgsConstructor
public class FreeDayApi {
    private final FreeDayService freeDayService;

    @PostMapping
    public ApiResponse<FreeDayResponse> add(@RequestHeader("email") String email, @RequestBody AddFreeDayRequest addFreeDayRequest){
        return freeDayService.add(email, addFreeDayRequest);
    }

    @PutMapping("/{freeDayId}")
    public ApiResponse<FreeDayResponse> update(@RequestHeader("email") String email, @PathVariable("freeDayId") Long id, @RequestBody AddFreeDayRequest addFreeDayRequest){
        return freeDayService.update(email, id, addFreeDayRequest);
    }

    @DeleteMapping("/{freeDayId}")
    public ApiResponse<FreeDayResponse> delete(@RequestHeader("email") String email, @PathVariable("freeDayId") Long id){
        return freeDayService.delete(email, id);
    }
    @GetMapping
    public ApiResponse<PaginationDto<List<FreeDayResponse>>> list(@RequestHeader("email") String email, @RequestParam int offset, @RequestParam int limit){
        return freeDayService.list(email, offset, limit);
    }
}
