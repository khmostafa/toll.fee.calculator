package com.city.traffic.toll.fee.calculator.freevehicle.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.request.FreeVehiclePayload;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.response.FreeVehicleResponse;
import com.city.traffic.toll.fee.calculator.freevehicle.service.FreeVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/free-vehicle")
@RequiredArgsConstructor
public class FreeVehicleApi {
    private final FreeVehicleService freeDayService;

    @PostMapping
    public ApiResponse<FreeVehicleResponse> add(@RequestHeader("email") String email, @RequestBody FreeVehiclePayload freeVehiclePayload){
        return freeDayService.add(email, freeVehiclePayload);
    }

    @PutMapping("/{freeVehicleId}")
    public ApiResponse<FreeVehicleResponse> update(@RequestHeader("email") String email, @PathVariable("freeVehicleId") Long id, @RequestBody FreeVehiclePayload freeVehiclePayload){
        return freeDayService.update(email, id, freeVehiclePayload);
    }

    @DeleteMapping("/{freeVehicleId}")
    public ApiResponse<FreeVehicleResponse> delete(@RequestHeader("email") String email, @PathVariable("freeVehicleId") Long id){
        return freeDayService.delete(email, id);
    }
    @GetMapping
    public ApiResponse<PaginationDto<List<FreeVehicleResponse>>> list(@RequestHeader("email") String email, @RequestParam int offset, @RequestParam int limit){
        return freeDayService.list(email, offset, limit);
    }
}
