package com.city.traffic.toll.fee.calculator.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationDto<T> {
    private T data;

    private Long totalElements;

    private Integer totalPages;
}
