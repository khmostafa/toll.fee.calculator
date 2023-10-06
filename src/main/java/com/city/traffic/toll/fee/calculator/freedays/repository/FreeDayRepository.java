package com.city.traffic.toll.fee.calculator.freedays.repository;

import com.city.traffic.toll.fee.calculator.common.repository.BaseRepository;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FreeDayRepository extends BaseRepository<FreeDayEntity> {

    @Query("Select fd.freeDay from FreeDayEntity fd")
    List<LocalDate> getFreeDays();
}
