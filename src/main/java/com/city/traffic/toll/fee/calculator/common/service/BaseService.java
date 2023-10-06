package com.city.traffic.toll.fee.calculator.common.service;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.mapper.BaseMapper;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.common.model.model.EntityBase;
import com.city.traffic.toll.fee.calculator.common.repository.BaseRepository;
import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.common.specification.BaseSpecification;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import java.util.List;

@Log4j2
@Data
@RequiredArgsConstructor
public class BaseService<E extends EntityBase, T extends BaseRepository<E>, F extends BaseSpecification<E>, R, M extends BaseMapper<E, R, S>, S, P extends UserPermissionService<E, R>> {

    private final M mapper;
    private final T repository;
    private final F specification;
    private final P permission;

    public  ApiResponse<S> add(String email, R request){
        EmployeeEntity user = permission.getUserIfApprovedForThisRequest(email, request);
        log.info("User {} Need To Add Record By {}", user.getName(), request.getClass().getSimpleName());
        E entity = mapper.toEntity(request);
        entity = repository.save(entity, user.getId());
        log.info("{} Record Added", entity.getClass().getSimpleName());
        return ApiResponse.created(mapper.toResponse(entity));
    }

    public ApiResponse<S> update(String email, Long id, R request){
        EmployeeEntity user = permission.getUserIfApprovedForThisRequest(email, request);
        E entity = repository.findById(id).orElseThrow(() -> new STCValidationException(ErrorKeys.CAN_NOT_FIND_FREE_DAY_WITH_THIS_ID, HttpStatus.BAD_REQUEST));
        permission.checkIfEntityApprovedToBeManipulatedByThisUser(email, entity);
        log.info("User {} Need To Apply Update Record With Id: {} By {}", user.getName(), id, request.getClass().getSimpleName());
        entity = mapper.updateEntity(request, entity);
        repository.update(entity, user.getId());
        log.info("{} Record With Id {} Updated", entity.getClass().getSimpleName(), id);
        return ApiResponse.created(mapper.toResponse(entity));
    }

    public ApiResponse<S> delete(String email, Long id){
        EmployeeEntity user = permission.getEmployeeProfile(email);
        E entity = repository.findById(id).orElseThrow(() -> new STCValidationException(ErrorKeys.CAN_NOT_FIND_FREE_DAY_WITH_THIS_ID, HttpStatus.BAD_REQUEST));
        permission.checkIfEntityApprovedToBeManipulatedByThisUser(email, entity);
        log.info("User {} Need delete {} With Id: {}", user.getName(), entity.getClass().getSimpleName(), id);
        repository.delete(entity);
        log.info("{} Record With Id {} Deleted", entity.getClass().getSimpleName(), id);
        return ApiResponse.created(mapper.toResponse(entity));
    }

    public ApiResponse<PaginationDto<List<S>>> list(String email, int offset, int limit){
        EmployeeEntity user = permission.getEmployeeProfile(email);
        log.info("User {} Need To List Records", user.getName());
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        Page<E> ePage = repository.findAll(specification.getSpecification(user.getRole()), pageRequest);
        List<S> responses = ePage.getContent().stream()
                .map(mapper::toResponse)
                .toList();

        responses.forEach(r -> log.info("Record Displayed {}", r.getClass().getSimpleName()));

        return ApiResponse.ok(PaginationDto.<List<S>>builder()
                .totalElements(ePage.getTotalElements())
                .totalPages(ePage.getTotalPages())
                .data(responses).build());
    }
}
