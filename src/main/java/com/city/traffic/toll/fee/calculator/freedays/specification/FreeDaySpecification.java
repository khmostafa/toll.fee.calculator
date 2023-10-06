package com.city.traffic.toll.fee.calculator.freedays.specification;

import com.city.traffic.toll.fee.calculator.common.specification.BaseSpecification;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FreeDaySpecification implements BaseSpecification<FreeDayEntity> {
    @Override
    public Specification<FreeDayEntity> getSpecification(EmployeeRole role) {
        return (Root<FreeDayEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.conjunction();
    }
}
