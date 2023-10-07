package com.city.traffic.toll.fee.calculator.user.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.EmployeePayload;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
import com.city.traffic.toll.fee.calculator.user.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@Validated
public class EmployeeApi {
    private final EmployeeService employeeService;

    @PostMapping
    public ApiResponse<EmployeeResponse> add(@RequestHeader("email") String email, @Valid @RequestBody EmployeePayload employeePayload){
        return employeeService.add(email, employeePayload);
    }

    @PutMapping("/{employeeId}")
    public ApiResponse<EmployeeResponse> update(@RequestHeader("email") String email, @Valid @PathVariable("employeeId") Long id, @RequestBody EmployeePayload employeePayload){
        return employeeService.update(email, id, employeePayload);
    }

    @DeleteMapping("/{employeeId}")
    public ApiResponse<EmployeeResponse> delete(@RequestHeader("email") String email, @PathVariable("employeeId") Long id){
        return employeeService.delete(email, id);
    }

    @GetMapping
    public ApiResponse<PaginationDto<List<EmployeeResponse>>> list(@RequestHeader("email") String email, @RequestParam int offset, @RequestParam int limit){
        return employeeService.list(email, offset, limit);
    }
}
