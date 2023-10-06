package com.city.traffic.toll.fee.calculator.freevehicletype.mapper;

import com.city.traffic.toll.fee.calculator.common.mapper.BaseMapper;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.request.AddFreeVehicleRequest;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.response.FreeVehicleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FreeVehicleMapper extends BaseMapper<FreeVehicleEntity, AddFreeVehicleRequest, FreeVehicleResponse> {
}