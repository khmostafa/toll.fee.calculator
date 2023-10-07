package com.city.traffic.toll.fee.calculator.toll.specification;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.common.specification.BaseSpecification;
import com.city.traffic.toll.fee.calculator.toll.model.entity.TollEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TollSpecification implements BaseSpecification<TollEntity> {
    @Override
    public Specification<TollEntity> getSpecification(EmployeeRole role) {
        return (Root<TollEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.conjunction();
    }

    public Specification<TollEntity> vehicleSpec(String vehicleNo) {
        return (Root<TollEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                vehicleNo == null || vehicleNo.isEmpty() ?
                criteriaBuilder.conjunction()
                : criteriaBuilder.equal(criteriaBuilder.lower(root.get("vehicleNo")), vehicleNo.toLowerCase());
    }
}
