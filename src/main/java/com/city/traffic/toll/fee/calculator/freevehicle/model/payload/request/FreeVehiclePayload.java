package com.city.traffic.toll.fee.calculator.freevehicle.model.payload.request;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class FreeVehiclePayload implements Serializable {
    @NotNull(message = ErrorKeys.FREE_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY)
    @NotEmpty(message = ErrorKeys.FREE_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY)
    @Length(max = 20, message = ErrorKeys.FREE_VEHICLE_TYPE_LENGTH_MUST_NOT_EXCEED_20)
    @Pattern(regexp = "^[A-Z_]*$", message = ErrorKeys.FREE_VEHICLE_TYPE_MUST_BE_IN_APPROVED_FORMAT)
    @Column(unique = true)
    private String type;
}
