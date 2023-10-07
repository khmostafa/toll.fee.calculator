package com.city.traffic.toll.fee.calculator.freedays.model.payload.request;

import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class FreeDayPayload implements Serializable {
    @NotNull(message = ErrorKeys.FREE_DAY_NAME_MUST_NOT_BE_NULL_OR_EMPTY)
    @NotEmpty(message = ErrorKeys.FREE_DAY_NAME_MUST_NOT_BE_NULL_OR_EMPTY)
    @Length(max = 100, message = ErrorKeys.FREE_DAY_NAME_LENGTH_MUST_NOT_EXCEED_100)
    private String name;

    @NotNull(message = ErrorKeys.FREE_DAY_DATE_MUST_NOT_BE_NULL)
    @Column(unique = true)
    private LocalDate freeDay;
}
