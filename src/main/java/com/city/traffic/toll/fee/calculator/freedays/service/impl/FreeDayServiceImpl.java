package com.city.traffic.toll.fee.calculator.freedays.service.impl;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.common.service.UserPermissionService;
import com.city.traffic.toll.fee.calculator.freedays.mapper.FreeDayMapper;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.AddFreeDayRequest;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.freedays.repository.FreeDayRepository;
import com.city.traffic.toll.fee.calculator.freedays.service.FreeDayService;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class FreeDayServiceImpl implements FreeDayService {
    private final FreeDayMapper freeDayMapper;
    private final FreeDayRepository freeDayRepository;
    private final UserPermissionService userPermissionService;
    @Override
    public ApiResponse<FreeDayResponse> add(AddFreeDayRequest addFreeDayRequest) {
        log.info("Need Add Free Day: {}", addFreeDayRequest);
        EmployeeEntity user = userPermissionService.getUserIfApproved(EmployeeRole.JUST_USER);
        FreeDayEntity freeDayEntity = freeDayMapper.toEntity(addFreeDayRequest);
        freeDayEntity = freeDayRepository.save(freeDayEntity, user.getId());
        log.info("Free Day Added ------------------------------");
        return ApiResponse.created(freeDayMapper.toResponse(freeDayEntity));
    }

    @Override
    public ApiResponse<FreeDayResponse> delete(Long id) {
        log.info("Need Add Free Day With Id: {}", id);
        userPermissionService.isApprovedUser(EmployeeRole.JUST_USER);
        FreeDayEntity freeDayEntity = freeDayRepository.findById(id).orElseThrow(() -> new STCValidationException(ErrorKeys.CAN_NOT_FIND_FREE_DAY_WITH_THIS_ID, HttpStatus.BAD_REQUEST));
        freeDayRepository.delete(freeDayEntity);
        log.info("Free Day Deleted ------------------------------");
        return ApiResponse.created(freeDayMapper.toResponse(freeDayEntity));
    }

    @Override
    public ApiResponse<PaginationDto<List<FreeDayResponse>>> list(int offset, int limit) {
        log.info("Need List Employees --------------------");
        userPermissionService.isApprovedUser(EmployeeRole.JUST_USER);
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        Page<FreeDayEntity> freeDayEntityPage = freeDayRepository.findAll(pageRequest);
        List<FreeDayResponse> freeDayResponses = freeDayEntityPage.getContent().stream()
                .map(freeDayMapper::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.ok(PaginationDto.<List<FreeDayResponse>>builder()
                .totalElements(freeDayEntityPage.getTotalElements())
                .totalPages(freeDayEntityPage.getTotalPages())
                .data(freeDayResponses).build());
    }
}
