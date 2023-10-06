package com.city.traffic.toll.fee.calculator.hourfee.specification;

import com.city.traffic.toll.fee.calculator.common.specification.BaseSpecification;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class HourFeeSpecification implements BaseSpecification<HourFeeEntity> {
    @Override
    public Specification<HourFeeEntity> getSpecification(EmployeeRole role) {
        return (Root<HourFeeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.conjunction();
    }
}
