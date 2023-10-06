package com.city.traffic.toll.fee.calculator.freevehicletype.model.entity;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.model.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Entity
@Table(name = "free_vehicle")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FreeVehicleEntity extends EntityBase {
    @NotNull(message = ErrorKeys.FREE_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY)
    @NotEmpty(message = ErrorKeys.FREE_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY)
    @Length(max = 100, message = ErrorKeys.FREE_VEHICLE_TYPE_LENGTH_MUST_NOT_EXCEED_100)
    @Pattern(regexp = "^[A-Z_]*$", message = ErrorKeys.FREE_VEHICLE_TYPE_MUST_BE_IN_APPROVED_FORMAT)
    private String type;
}
