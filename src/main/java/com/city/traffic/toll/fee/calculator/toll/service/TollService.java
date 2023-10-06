package com.city.traffic.toll.fee.calculator.toll.service;

import com.city.traffic.toll.fee.calculator.common.service.BaseService;
import com.city.traffic.toll.fee.calculator.hourfee.mapper.HourFeeMapper;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.response.HourFeeResponse;
import com.city.traffic.toll.fee.calculator.toll.mapper.TollMapper;
import com.city.traffic.toll.fee.calculator.toll.model.entity.TollEntity;
import com.city.traffic.toll.fee.calculator.toll.model.payload.request.TollPayload;
import com.city.traffic.toll.fee.calculator.toll.model.payload.response.TollResponse;
import com.city.traffic.toll.fee.calculator.toll.permission.TollPermission;
import com.city.traffic.toll.fee.calculator.toll.repository.TollRepository;
import com.city.traffic.toll.fee.calculator.toll.specification.TollSpecification;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TollService extends BaseService<TollEntity, TollRepository, TollSpecification, TollPayload, TollMapper, TollResponse, TollPermission> {
    public TollService(TollMapper mapper, TollRepository repository, TollSpecification specification, TollPermission permission) {
        super(mapper, repository, specification, permission);
    }
}
