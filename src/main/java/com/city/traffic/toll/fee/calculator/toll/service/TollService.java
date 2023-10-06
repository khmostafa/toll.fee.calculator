package com.city.traffic.toll.fee.calculator.toll.service;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.common.service.BaseService;
import com.city.traffic.toll.fee.calculator.freedays.repository.FreeDayRepository;
import com.city.traffic.toll.fee.calculator.freevehicle.repository.FreeVehicleRepository;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.hourfee.repository.HourFeeRepository;
import com.city.traffic.toll.fee.calculator.toll.mapper.TollMapper;
import com.city.traffic.toll.fee.calculator.toll.model.entity.TollEntity;
import com.city.traffic.toll.fee.calculator.toll.model.payload.request.TollPayload;
import com.city.traffic.toll.fee.calculator.toll.model.payload.response.FeeResponse;
import com.city.traffic.toll.fee.calculator.toll.model.payload.response.TollResponse;
import com.city.traffic.toll.fee.calculator.toll.permission.TollPermission;
import com.city.traffic.toll.fee.calculator.toll.repository.TollRepository;
import com.city.traffic.toll.fee.calculator.toll.specification.TollSpecification;
import com.city.traffic.toll.fee.calculator.toll.tollcalculator.TollCalculator;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
public class TollService extends BaseService<TollEntity, TollRepository, TollSpecification, TollPayload, TollMapper, TollResponse, TollPermission> {

    private FreeDayRepository freeDayRepository;
    private FreeVehicleRepository freeVehicleRepository;
    private HourFeeRepository hourFeeRepository;
    private TollCalculator tollCalculator;

    public TollService(TollMapper mapper, TollRepository repository, TollSpecification specification, TollPermission permission, FreeDayRepository freeDayRepository, FreeVehicleRepository freeVehicleRepository, HourFeeRepository hourFeeRepository, TollCalculator tollCalculator) {
        super(mapper, repository, specification, permission);
        this.freeDayRepository = freeDayRepository;
        this.freeVehicleRepository = freeVehicleRepository;
        this.hourFeeRepository = hourFeeRepository;
        this.tollCalculator = tollCalculator;
    }
    
    public ApiResponse<PaginationDto<List<FeeResponse>>> tollFee(String email, String vehicleNo, int offset, int limit){
        log.info("User '{}' need to list toll fees report for vehicle '{}'", email, vehicleNo);
        EmployeeEntity user = this.getPermission().getEmployeeProfile(email);
        List<TollEntity> tolls = this.getRepository().getTollDates(vehicleNo);
        List<String> freeTypes = freeVehicleRepository.getFreeTypes();
        List<LocalDate> freeDays = freeDayRepository.getFreeDays();
        List<HourFeeEntity> fees = hourFeeRepository.findAll();
        PaginationDto<List<FeeResponse>> page = tollCalculator.getTotalFee(offset, limit, tolls, freeTypes, freeDays, fees);
        page.getData().forEach(r -> log.info("User '{}' list {}", user.getEmail(), r));
        return ApiResponse.ok(page);
    }

    public ApiResponse<PaginationDto<List<TollResponse>>> listTolls(String email, String vehicleNo, int offset, int limit){
        log.info("User '{}' need to list toll history for vehicle '{}'", email, vehicleNo);
        EmployeeEntity user = this.getPermission().getEmployeeProfile(email);
        List<TollEntity> tolls = this.getRepository().getTollDates(vehicleNo);
        List<TollResponse> responses = tolls.stream().map(t -> this.getMapper().toResponse(t)).toList();
        PaginationDto<List<TollResponse>> page = tollCalculator.getTollPage(offset, limit, responses);
        page.getData().forEach(r -> log.info("User '{}' list record {}", user.getEmail(), r));
        return ApiResponse.ok(page);
    }
}
