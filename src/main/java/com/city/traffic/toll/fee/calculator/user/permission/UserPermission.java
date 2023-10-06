package com.city.traffic.toll.fee.calculator.user.permission;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.AddEmployeeRequest;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;

public class UserPermission extends UserPermissionService<EmployeeEntity, AddEmployeeRequest> {
    public UserPermission(EmployeeRepository employeeRepository) {
        super(employeeRepository);
    }


    @Override
    public EmployeeEntity getUserIfApprovedForThisRequest(String email, AddEmployeeRequest request) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(request.getRole() == null || request.getRole().getLongValue() >= user.getRole().getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
        return user;
    }

    @Override
    public void checkIfEntityApprovedToBeManipulatedByThisUser(String email, EmployeeEntity entity) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(entity.getRole().getLongValue() >= user.getRole().getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
    }
}
