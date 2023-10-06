package com.city.traffic.toll.fee.calculator.hourfee.permission;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.AddHourFeeRequest;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;

public class HourFeePermission extends UserPermissionService<HourFeeEntity, AddHourFeeRequest> {

    private final EmployeeRole role = EmployeeRole.JUST_USER;

    public HourFeePermission(EmployeeRepository employeeRepository) {
        super(employeeRepository);
    }

    @Override
    public EmployeeEntity getUserIfApprovedForThisRequest(String email, AddHourFeeRequest request) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= this.role.getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
        return user;
    }

    @Override
    public void checkIfEntityApprovedToBeManipulatedByThisUser(String email, HourFeeEntity entity) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= this.role.getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
    }
}