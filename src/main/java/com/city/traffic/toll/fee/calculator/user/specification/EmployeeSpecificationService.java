package com.city.traffic.toll.fee.calculator.user.specification;

import com.city.traffic.toll.fee.calculator.common.specification.BaseSpecification;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSpecificationService implements BaseSpecification<EmployeeEntity> {

    public Specification<EmployeeEntity> roleSpec(EmployeeRole role) {
        return (Root<EmployeeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
            criteriaBuilder.lessThan(root.get("role").get("value"), role.getLongValue());
    }

    @Override
    public Specification<EmployeeEntity> getSpecification(EmployeeRole role) {
        return Specification.where(roleSpec(role));
    }
}
