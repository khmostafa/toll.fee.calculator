package com.city.traffic.toll.fee.calculator.freedays.permission;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.FreeDayPayload;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FreeDayPermission extends UserPermissionService<FreeDayEntity, FreeDayPayload> {

    private static final EmployeeRole role = EmployeeRole.JUST_USER;
    public FreeDayPermission(EmployeeRepository employeeRepository) {
        super(employeeRepository);
    }

    @Override
    public EmployeeEntity getUserIfApprovedForThisRequest(String email, FreeDayPayload request) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= role.getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
        return user;
    }

    @Override
    public void checkIfEntityApprovedToBeManipulatedByThisUser(String email, FreeDayEntity entity) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= role.getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
    }
}
