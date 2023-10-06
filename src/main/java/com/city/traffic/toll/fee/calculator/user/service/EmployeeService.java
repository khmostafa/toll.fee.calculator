package com.city.traffic.toll.fee.calculator.user.service;

import com.city.traffic.toll.fee.calculator.common.service.BaseService;
import com.city.traffic.toll.fee.calculator.user.mapper.EmployeeMapper;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.EmployeePayload;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
import com.city.traffic.toll.fee.calculator.user.permission.UserPermission;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import com.city.traffic.toll.fee.calculator.user.specification.EmployeeSpecificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class EmployeeService extends BaseService<EmployeeEntity, EmployeeRepository, EmployeeSpecificationService, EmployeePayload, EmployeeMapper, EmployeeResponse, UserPermission> {
    public EmployeeService(EmployeeMapper mapper, EmployeeRepository repository, EmployeeSpecificationService specification, UserPermission permission) {
        super(mapper, repository, specification, permission);
    }
}
