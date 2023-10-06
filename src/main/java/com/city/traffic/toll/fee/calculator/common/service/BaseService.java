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
        log.info("User '{}' need to add record by {}", email, request);
        EmployeeEntity user = permission.getUserIfApprovedForThisRequest(email, request);
        E entity = mapper.toEntity(request);
        entity = repository.save(entity, user.getId());
        log.info("user '{}' added {}", email, entity);
        return ApiResponse.created(mapper.toResponse(entity));
    }

    public ApiResponse<S> update(String email, Long id, R request){
        log.info("User '{}' need to update record with id ({}) by {}", email, id, request);
        EmployeeEntity user = permission.getUserIfApprovedForThisRequest(email, request);
        E entity = repository.findById(id).orElseThrow(() -> new STCValidationException(ErrorKeys.CAN_NOT_FIND_FREE_DAY_WITH_THIS_ID, HttpStatus.BAD_REQUEST));
        log.info("User '{}' need to update {}", user.getEmail(), entity);
        permission.checkIfEntityApprovedToBeManipulatedByThisUser(user.getEmail(), entity);
        entity = mapper.updateEntity(request, entity);
        entity = repository.update(entity, user.getId());
        log.info("user '{}' updated {}", email, entity);
        return ApiResponse.created(mapper.toResponse(entity));
    }

    public ApiResponse<S> delete(String email, Long id){
        log.info("User '{}' need to delete record with id {}", email, id);
        EmployeeEntity user = permission.getEmployeeProfile(email);
        E entity = repository.findById(id).orElseThrow(() -> new STCValidationException(ErrorKeys.CAN_NOT_FIND_FREE_DAY_WITH_THIS_ID, HttpStatus.BAD_REQUEST));
        log.info("User '{}' need to delete {}", user.getEmail(), entity);
        permission.checkIfEntityApprovedToBeManipulatedByThisUser(email, entity);
        repository.deleteEntity(entity);
        log.info("user '{}' deleted {}", email, entity);
        return ApiResponse.created(mapper.toResponse(entity));
    }

    public ApiResponse<PaginationDto<List<S>>> list(String email, int offset, int limit){
        log.info("User '{}' need to list records", email);
        EmployeeEntity user = permission.getEmployeeProfile(email);
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        Page<E> ePage = repository.findAll(specification.getSpecification(user.getRole()), pageRequest);
        List<S> responses = ePage.getContent().stream()
                .map(mapper::toResponse)
                .toList();

        responses.forEach(r -> log.info("User '{}' list {}", email, r));

        return ApiResponse.ok(PaginationDto.<List<S>>builder()
                .totalElements(ePage.getTotalElements())
                .totalPages(ePage.getTotalPages())
                .data(responses).build());
    }
}
