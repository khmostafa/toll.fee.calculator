package com.city.traffic.toll.fee.calculator.freedays.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.FreeDayPayload;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.freedays.service.FreeDayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/free-day")
@RequiredArgsConstructor
@Validated
public class FreeDayApi {
    private final FreeDayService freeDayService;

    @PostMapping
    public ApiResponse<FreeDayResponse> add(@RequestHeader("email") String email, @Valid @RequestBody FreeDayPayload freeDayPayload){
        return freeDayService.add(email, freeDayPayload);
    }

    @PutMapping("/{freeDayId}")
    public ApiResponse<FreeDayResponse> update(@RequestHeader("email") String email, @Valid @PathVariable("freeDayId") Long id, @RequestBody FreeDayPayload freeDayPayload){
        return freeDayService.update(email, id, freeDayPayload);
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
