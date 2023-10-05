package com.city.traffic.toll.fee.calculator.user.service.impl;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.common.service.UserPermissionService;
import com.city.traffic.toll.fee.calculator.user.mapper.EmployeeMapper;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.AddEmployeeRequest;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import com.city.traffic.toll.fee.calculator.user.service.EmployeeService;
import com.city.traffic.toll.fee.calculator.user.specification.EmployeeSpecificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final UserPermissionService userPermissionService;
    private final EmployeeSpecificationService employeeSpecificationService;


    @Override
    public ApiResponse<EmployeeResponse> add(AddEmployeeRequest addEmployeeRequest) {
        log.info("Need Add User: {}", addEmployeeRequest);
        EmployeeEntity user = userPermissionService.getUserIfApproved(addEmployeeRequest.getRole());
        EmployeeEntity employeeEntity = employeeMapper.toEntity(addEmployeeRequest);
        employeeEntity = employeeRepository.save(employeeEntity, user.getId());
        log.info("User Added --------------------");
        return ApiResponse.created(employeeMapper.toResponse(employeeEntity));
    }

    @Override
    public ApiResponse<EmployeeResponse> update(Long id, AddEmployeeRequest addEmployeeRequest) {
        log.info("Need Update User With ID: {} by {}", id, addEmployeeRequest);
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() -> new STCValidationException("", HttpStatus.BAD_REQUEST));
        EmployeeEntity user = userPermissionService.getUserIfApproved(employeeEntity.getRole());
        employeeEntity = employeeMapper.updateEntity(addEmployeeRequest, employeeEntity);
        employeeEntity = employeeRepository.update(employeeEntity, user.getId());
        log.info("User Updated --------------------");
        return ApiResponse.accepted(employeeMapper.toResponse(employeeEntity));
    }

    @Override
    public ApiResponse<EmployeeResponse> delete(Long id) {
        log.info("Need Delete User With ID: {}", id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() -> new STCValidationException("", HttpStatus.BAD_REQUEST));
        userPermissionService.isApprovedUser(employeeEntity.getRole());
        employeeRepository.delete(employeeEntity);
        log.info("User Deleted --------------------");
        return ApiResponse.accepted(employeeMapper.toResponse(employeeEntity));
    }

    @Override
    public ApiResponse<PaginationDto<List<EmployeeResponse>>> list(int offset, int limit) {
        log.info("Need List Employees --------------------");
        EmployeeEntity user = userPermissionService.getUserIfApproved(EmployeeRole.EMPLOYEE);
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        Specification<EmployeeEntity> listSpecification = employeeSpecificationService.listEmployeeSpecification(user.getRole());
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(listSpecification, pageRequest);
        List<EmployeeResponse> employeeResponses = employeeEntityPage.getContent().stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.ok(PaginationDto.<List<EmployeeResponse>>builder()
                .totalElements(employeeEntityPage.getTotalElements())
                .totalPages(employeeEntityPage.getTotalPages())
                .data(employeeResponses).build());
    }
}
