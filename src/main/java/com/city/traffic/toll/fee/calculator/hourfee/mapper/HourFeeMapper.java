package com.city.traffic.toll.fee.calculator.hourfee.mapper;

import com.city.traffic.toll.fee.calculator.common.mapper.BaseMapper;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.AddHourFeeRequest;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.response.HourFeeResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HourFeeMapper extends BaseMapper<HourFeeEntity, AddHourFeeRequest, HourFeeResponse> {

}
