package com.city.traffic.toll.fee.calculator.toll.repository;

import com.city.traffic.toll.fee.calculator.common.repository.BaseRepository;
import com.city.traffic.toll.fee.calculator.toll.model.entity.TollEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TollRepository extends BaseRepository<TollEntity> {
    @Query("select t from TollEntity t where t.vehicleNo = :vehicleNo order by t.date asc")
    List<TollEntity> getTollDates(@Param("vehicleNo") String vehicleNo);
}
