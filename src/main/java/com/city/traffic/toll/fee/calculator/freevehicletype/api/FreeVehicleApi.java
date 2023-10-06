package com.city.traffic.toll.fee.calculator.freevehicletype.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.request.AddFreeVehicleRequest;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.response.FreeVehicleResponse;
import com.city.traffic.toll.fee.calculator.freevehicletype.service.FreeVehicleService;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/free-vehicle")
@RequiredArgsConstructor
public class FreeVehicleApi {
    private final FreeVehicleService freeDayService;

    @PostMapping
    public ApiResponse<FreeVehicleResponse> add(@RequestHeader("email") String email, @RequestBody AddFreeVehicleRequest addFreeVehicleRequest){
        return freeDayService.add(email, addFreeVehicleRequest);
    }

    @PutMapping("/{freeDayId}")
    public ApiResponse<FreeVehicleResponse> update(@RequestHeader("email") String email, @PathVariable("freeDayId") Long id, @RequestBody AddFreeVehicleRequest addFreeVehicleRequest){
        return freeDayService.update(email, id, addFreeVehicleRequest);
    }

    @DeleteMapping("/{freeDayId}")
    public ApiResponse<FreeVehicleResponse> delete(@RequestHeader("email") String email, @PathVariable("freeDayId") Long id){
        return freeDayService.delete(email, id);
    }
    @GetMapping
    public ApiResponse<PaginationDto<List<FreeVehicleResponse>>> list(@RequestHeader("email") String email, @RequestParam int offset, @RequestParam int limit){
        return freeDayService.list(email, offset, limit);
    }
}
