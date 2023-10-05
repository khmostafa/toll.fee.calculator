package com.city.traffic.toll.fee.calculator.user.repository;

import com.city.traffic.toll.fee.calculator.common.model.model.repository.BaseRepository;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;

import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<EmployeeEntity> {
    Optional<EmployeeEntity> findByEmail(String email);
}
