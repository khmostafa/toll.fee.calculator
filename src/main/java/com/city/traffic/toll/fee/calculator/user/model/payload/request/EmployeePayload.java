package com.city.traffic.toll.fee.calculator.user.model.payload.request;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePayload implements Serializable {
    @NotNull(message = ErrorKeys.EMPLOYEE_NAME_MUST_NOT_BE_NULL_OR_EMPTY)
    @NotEmpty(message = ErrorKeys.EMPLOYEE_NAME_MUST_NOT_BE_NULL_OR_EMPTY)
    @Length(max = 100, message = ErrorKeys.EMPLOYEE_NAME_LENGTH_MUST_NOT_EXCEED_100)
    private String name;

    @NotNull(message = ErrorKeys.EMPLOYEE_EMAIL_MUST_NOT_BE_NULL)
    @Email(message = ErrorKeys.EMPLOYEE_EMAIL_MUST_BE_IN_VALID_FORMAT)
    @Column(unique = true)
    private String email;

    @NotNull(message = ErrorKeys.EMPLOYEE_PROFILE_IMAGE_MUST_NOT_BE_NULL)
    private String profileImage;

    @NotNull(message = ErrorKeys.EMPLOYEE_ROLE_MUST_NOT_BE_NULL)
    private EmployeeRole role;
}
