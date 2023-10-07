package com.city.traffic.toll.fee.calculator.hourfee.model.entity;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.model.EntityBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "hour_fee")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class HourFeeEntity extends EntityBase {
    @NotNull(message = ErrorKeys.HOUR_FEE_START_TIME_MUST_NOT_BE_NULL)
    private LocalTime startTime;

    @NotNull(message = ErrorKeys.HOUR_FEE_END_TIME_MUST_NOT_BE_NULL)
    private LocalTime endTime;

    @NotNull(message = ErrorKeys.HOUR_FEE_VALUE_TIME_MUST_NOT_BE_NULL)
    @Min(value = 1, message = ErrorKeys.HOUR_FEE_VALUE_TIME_MUST_NOT_BE_NULL)
    private Long value;
}
