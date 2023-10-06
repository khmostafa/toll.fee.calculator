package com.city.traffic.toll.fee.calculator.freevehicletype.permission;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.common.permission.UserPermissionService;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.freevehicletype.model.payload.request.AddFreeVehicleRequest;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;

public class FreeVehiclePermission extends UserPermissionService<FreeVehicleEntity, AddFreeVehicleRequest> {

    private final EmployeeRole role = EmployeeRole.JUST_USER;

    public FreeVehiclePermission(EmployeeRepository employeeRepository) {
        super(employeeRepository);
    }

    @Override
    public EmployeeEntity getUserIfApprovedForThisRequest(String email, AddFreeVehicleRequest request) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= this.role.getLongValue()){
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
        return user;
    }

    @Override
    public void checkIfEntityApprovedToBeManipulatedByThisUser(String email, FreeVehicleEntity entity) {
        EmployeeEntity user = this.getEmployeeProfile(email);
        if(user.getRole().getLongValue() <= this.role.getLongValue()) {
            throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
        }
    }
}
