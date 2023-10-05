package com.city.traffic.toll.fee.calculator.freedays.model.entity;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.model.entity.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@Table(name = "free_day")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FreeDayEntity extends EntityBase {
    @NotNull(message = ErrorKeys.FREE_DAY_NAME_MUST_NOT_BE_NULL_OR_EMPTY)
    @NotEmpty(message = ErrorKeys.FREE_DAY_NAME_MUST_NOT_BE_NULL_OR_EMPTY)
    @Length(max = 100, message = ErrorKeys.FREE_DAY_NAME_LENGTH_MUST_NOT_EXCEED_100)
    private String name;

    @NotNull(message = ErrorKeys.FREE_DAY_DATE_MUST_NOT_BE_NULL)
    private LocalDate freeDay;
}
