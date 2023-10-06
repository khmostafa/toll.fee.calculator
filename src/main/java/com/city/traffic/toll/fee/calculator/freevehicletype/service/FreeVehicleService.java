package com.city.traffic.toll.fee.calculator.freevehicletype.service;

import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.common.service.BaseService;
import com.city.traffic.toll.fee.calculator.freevehicletype.mapper.FreeVehicleMapper;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.request.AddFreeVehicleRequest;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.response.FreeVehicleResponse;
import com.city.traffic.toll.fee.calculator.freevehicletype.permission.FreeVehiclePermission;
import com.city.traffic.toll.fee.calculator.freevehicletype.repository.FreeVehicleRepository;
import com.city.traffic.toll.fee.calculator.freevehicletype.specification.FreeVehicleSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FreeVehicleService extends BaseService<FreeVehicleEntity, FreeVehicleRepository, FreeVehicleSpecification, AddFreeVehicleRequest, FreeVehicleMapper, FreeVehicleResponse, FreeVehiclePermission> {
    public FreeVehicleService(FreeVehicleMapper mapper, FreeVehicleRepository repository, FreeVehicleSpecification specification, FreeVehiclePermission permission) {
        super(mapper, repository, specification, permission);
    }
}
