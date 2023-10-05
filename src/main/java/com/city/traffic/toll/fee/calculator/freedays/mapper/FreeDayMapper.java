package com.city.traffic.toll.fee.calculator.freedays.mapper;

import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.AddFreeDayRequest;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FreeDayMapper {
    FreeDayEntity toEntity(AddFreeDayRequest addFreeDayRequest);
    FreeDayResponse toResponse(FreeDayEntity freeDayEntity);
    FreeDayEntity updateEntity(AddFreeDayRequest addFreeDayRequest, @MappingTarget FreeDayEntity freeDayEntity);
}