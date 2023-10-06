package com.city.traffic.toll.fee.calculator.user.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.AddEmployeeRequest;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
import com.city.traffic.toll.fee.calculator.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeApi {
    private final EmployeeService employeeService;

    @PostMapping
    public ApiResponse<EmployeeResponse> add(@RequestHeader("email") String email, @RequestBody AddEmployeeRequest addEmployeeRequest){
        return employeeService.add(email, addEmployeeRequest);
    }

    @PutMapping("/{employeeId}")
    public ApiResponse<EmployeeResponse> update(@RequestHeader("email") String email, @PathVariable("employeeId") Long id, @RequestBody AddEmployeeRequest addEmployeeRequest){
        return employeeService.update(email, id, addEmployeeRequest);
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
