package com.city.traffic.toll.fee.calculator.user.specification;

import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import org.springframework.data.jpa.domain.Specification;

public interface EmployeeSpecificationService {
    Specification<EmployeeEntity> listEmployeeSpecification(EmployeeRole role);
}
