package com.city.traffic.toll.fee.calculator.toll.permission;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.toll.model.entity.TollEntity;
import com.city.traffic.toll.fee.calculator.toll.model.payload.request.TollPayload;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TollPermission extends UserPermissionService<TollEntity, TollPayload> {

    private final EmployeeRole role = EmployeeRole.JUST_USER;

    public TollPermission(EmployeeRepository employeeRepository) {
        super(employeeRepository);
    }

    @Override
    public EmployeeEntity getUserIfApprovedForThisRequest(String email, TollPayload request) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= this.role.getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
        return user;
    }

    @Override
    public void checkIfEntityApprovedToBeManipulatedByThisUser(String email, TollEntity entity) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= this.role.getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
    }
}
