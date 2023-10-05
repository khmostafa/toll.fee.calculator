package com.city.traffic.toll.fee.calculator.user.service;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.AddEmployeeRequest;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    ApiResponse<EmployeeResponse> add(AddEmployeeRequest addEmployeeRequest);

    ApiResponse<EmployeeResponse> update(Long id, AddEmployeeRequest addEmployeeRequest);

    ApiResponse<EmployeeResponse> delete(Long id);

    ApiResponse<PaginationDto<List<EmployeeResponse>>> list(int offset, int limit);
}
