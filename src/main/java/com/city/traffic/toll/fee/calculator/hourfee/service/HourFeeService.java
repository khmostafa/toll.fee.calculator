package com.city.traffic.toll.fee.calculator.hourfee.service;

import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.common.service.BaseService;
import com.city.traffic.toll.fee.calculator.hourfee.mapper.HourFeeMapper;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.AddHourFeeRequest;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.response.HourFeeResponse;
import com.city.traffic.toll.fee.calculator.hourfee.permission.HourFeePermission;
import com.city.traffic.toll.fee.calculator.hourfee.repository.HourFeeRepository;
import com.city.traffic.toll.fee.calculator.hourfee.specification.HourFeeSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class HourFeeService extends BaseService<HourFeeEntity, HourFeeRepository, HourFeeSpecification, AddHourFeeRequest, HourFeeMapper, HourFeeResponse, HourFeePermission> {
    public HourFeeService(HourFeeMapper mapper, HourFeeRepository repository, HourFeeSpecification specification, HourFeePermission permission) {
        super(mapper, repository, specification, permission);
    }
}
