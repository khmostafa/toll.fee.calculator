package com.city.traffic.toll.fee.calculator.freevehicle.service;

import com.city.traffic.toll.fee.calculator.common.service.BaseService;
import com.city.traffic.toll.fee.calculator.freevehicle.mapper.FreeVehicleMapper;
import com.city.traffic.toll.fee.calculator.freevehicle.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.request.FreeVehiclePayload;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.response.FreeVehicleResponse;
import com.city.traffic.toll.fee.calculator.freevehicle.permission.FreeVehiclePermission;
import com.city.traffic.toll.fee.calculator.freevehicle.repository.FreeVehicleRepository;
import com.city.traffic.toll.fee.calculator.freevehicle.specification.FreeVehicleSpecification;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FreeVehicleService extends BaseService<FreeVehicleEntity, FreeVehicleRepository, FreeVehicleSpecification, FreeVehiclePayload, FreeVehicleMapper, FreeVehicleResponse, FreeVehiclePermission> {
    public FreeVehicleService(FreeVehicleMapper mapper, FreeVehicleRepository repository, FreeVehicleSpecification specification, FreeVehiclePermission permission) {
        super(mapper, repository, specification, permission);
    }
}
