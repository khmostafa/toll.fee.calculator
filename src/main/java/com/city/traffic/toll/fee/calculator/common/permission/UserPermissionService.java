package com.city.traffic.toll.fee.calculator.common.permission;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@RequiredArgsConstructor
public abstract class UserPermissionService<E, R> {

    private final EmployeeRepository employeeRepository;

    public EmployeeEntity getEmployeeProfile(String currentEmail) {
        return employeeRepository.findByEmail(currentEmail).orElseThrow(() -> new STCValidationException(ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM, HttpStatus.FORBIDDEN));
    }

    public EmployeeEntity getUserIfApproved(String email, EmployeeRole... roles){
        EmployeeEntity user = getEmployeeProfile(email);
        for (EmployeeRole role : roles){
            if(user.getRole().getLongValue() <= role.getLongValue()){
                throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
            }
        }
        return user;
    }

    public abstract EmployeeEntity getUserIfApprovedForThisRequest(String email, R request);

    public abstract void checkIfEntityApprovedToBeManipulatedByThisUser(String email, E entity);
}
