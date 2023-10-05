package com.city.traffic.toll.fee.calculator.user.api;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.AddEmployeeRequest;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
import com.city.traffic.toll.fee.calculator.user.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeApi {
    private final EmployeeService employeeService;

    @PostMapping
    public ApiResponse<EmployeeResponse> add(@RequestBody AddEmployeeRequest addEmployeeRequest){
        return employeeService.add(addEmployeeRequest);
    }

    @PutMapping("/{employeeId}")
    public ApiResponse<EmployeeResponse> update(@PathVariable("employeeId") Long id, @RequestBody AddEmployeeRequest addEmployeeRequest){
        return employeeService.update(id, addEmployeeRequest);
    }

    @DeleteMapping("/{employeeId}")
    public ApiResponse<EmployeeResponse> delete(@PathVariable("employeeId") Long id){
        return employeeService.delete(id);
    }
    @GetMapping
    public ApiResponse<PaginationDto<List<EmployeeResponse>>> list(@RequestParam int offset, @RequestParam int limit){
        return employeeService.list(offset, limit);
    }
}
