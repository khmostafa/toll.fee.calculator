package com.city.traffic.toll.fee.calculator.user.mapper;

import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.AddEmployeeRequest;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    EmployeeEntity toEntity(AddEmployeeRequest addEmployeeRequest);
    EmployeeResponse toResponse(EmployeeEntity employeeEntity);
    EmployeeEntity updateEntity(AddEmployeeRequest addEmployeeRequest, @MappingTarget EmployeeEntity employeeEntity);
}
