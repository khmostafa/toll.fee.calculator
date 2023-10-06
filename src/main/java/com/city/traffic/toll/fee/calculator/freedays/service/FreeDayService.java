package com.city.traffic.toll.fee.calculator.freedays.service;

import com.city.traffic.toll.fee.calculator.common.service.BaseService;
import com.city.traffic.toll.fee.calculator.freedays.mapper.FreeDayMapper;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.FreeDayPayload;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.freedays.permission.FreeDayPermission;
import com.city.traffic.toll.fee.calculator.freedays.repository.FreeDayRepository;
import com.city.traffic.toll.fee.calculator.freedays.specification.FreeDaySpecification;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FreeDayService extends BaseService<FreeDayEntity, FreeDayRepository, FreeDaySpecification, FreeDayPayload, FreeDayMapper, FreeDayResponse, FreeDayPermission> {
    public FreeDayService(FreeDayMapper mapper, FreeDayRepository repository, FreeDaySpecification specification, FreeDayPermission permission) {
        super(mapper, repository, specification, permission);
    }
}
