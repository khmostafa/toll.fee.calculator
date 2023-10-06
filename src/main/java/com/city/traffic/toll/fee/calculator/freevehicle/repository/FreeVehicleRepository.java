package com.city.traffic.toll.fee.calculator.freevehicle.repository;

import com.city.traffic.toll.fee.calculator.common.repository.BaseRepository;
import com.city.traffic.toll.fee.calculator.freevehicle.model.entity.FreeVehicleEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeVehicleRepository extends BaseRepository<FreeVehicleEntity> {
    @Query("select fv.type from FreeVehicleEntity fv")
    List<String> getFreeTypes();
}
