package com.city.traffic.toll.fee.calculator.user.specification.impl;

import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.specification.EmployeeSpecificationService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSpecificationServiceImpl implements EmployeeSpecificationService {
    @Override
    public Specification<EmployeeEntity> listEmployeeSpecification(EmployeeRole role) {
        return Specification.where(roleSpec(role));
    }

    public Specification<EmployeeEntity> roleSpec(EmployeeRole role) {
        return (Root<EmployeeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
            criteriaBuilder.lessThan(root.get("role").get("value"), role.getLongValue());
    }


}
