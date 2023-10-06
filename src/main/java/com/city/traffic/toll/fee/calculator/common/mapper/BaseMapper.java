package com.city.traffic.toll.fee.calculator.common.mapper;

import org.mapstruct.*;

public interface BaseMapper <E, R, S>{
    E toEntity(R r);
    S toResponse(E e);
    E updateEntity(R r, @MappingTarget E e);
}
