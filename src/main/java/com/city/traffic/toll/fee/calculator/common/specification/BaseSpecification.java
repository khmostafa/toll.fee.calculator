package com.city.traffic.toll.fee.calculator.common.specification;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import org.springframework.data.jpa.domain.Specification;

public interface BaseSpecification<E> {

    Specification<E> getSpecification(EmployeeRole role);

}
