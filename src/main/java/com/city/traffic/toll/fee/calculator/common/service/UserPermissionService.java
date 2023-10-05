package com.city.traffic.toll.fee.calculator.common.service;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.exception.STCValidationException;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserPermissionService {
    private final HttpServletRequest request;
    private final EmployeeRepository employeeRepository;

    private String getCurrentEmail() {
        String email = request.getHeader("email");
        log.info("current user email {}", email);
        if (StringUtils.isBlank(email))
            throw new STCValidationException(ErrorKeys.MUST_HAVE_EMAIL_HEADER_PARAMETER, HttpStatus.BAD_REQUEST);
        return email;
    }

    private EmployeeEntity getCurrentLoggedInUserProfile() {
        return getEmployeeProfile(getCurrentEmail());
    }

    private EmployeeEntity getEmployeeProfile(String currentEmail) {
        return employeeRepository.findByEmail(currentEmail).orElseThrow(() -> new STCValidationException(ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM, HttpStatus.FORBIDDEN));
    }

    public EmployeeEntity getUserIfApproved(EmployeeRole... roles){
        EmployeeEntity user = getCurrentLoggedInUserProfile();
        for (EmployeeRole role : roles){
            if(user.getRole().getLongValue() <= role.getLongValue()){
                throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
            }
        }
        return user;

    }

    public void isApprovedUser(EmployeeRole... roles){
        EmployeeEntity user = getCurrentLoggedInUserProfile();
        for (EmployeeRole role : roles){
            if(user.getRole().getLongValue() <= role.getLongValue()){
                throw new STCValidationException(ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION, HttpStatus.FORBIDDEN);
            }
        }
    }


}
