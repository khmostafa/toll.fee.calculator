package com.city.traffic.toll.fee.calculator.toll.model.entity;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.model.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;

@Entity
@Table(name = "tolls")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TollEntity extends EntityBase {
    @NotNull(message = ErrorKeys.TOLL_VEHICLE_NO_MUST_NOT_BE_NULL_OR_EMPTY)
    @NotEmpty(message = ErrorKeys.TOLL_VEHICLE_NO_MUST_NOT_BE_NULL_OR_EMPTY)
    @Length(max = 20, message = ErrorKeys.TOLL_VEHICLE_NO_LENGTH_MUST_NOT_EXCEED_20)
    private String vehicleNo;

    @NotNull(message = ErrorKeys.TOLL_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY)
    @NotEmpty(message = ErrorKeys.TOLL_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY)
    @Length(max = 20, message = ErrorKeys.TOLL_VEHICLE_TYPE_LENGTH_MUST_NOT_EXCEED_20)
    private String type;

    @NotNull(message = ErrorKeys.TOLL_DAY_MUST_NOT_BE_NULL)
    private LocalDateTime date;
}
