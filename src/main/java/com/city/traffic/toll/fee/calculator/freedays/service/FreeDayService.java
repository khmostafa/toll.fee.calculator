package com.city.traffic.toll.fee.calculator.freedays.service;

import com.city.traffic.toll.fee.calculator.common.exception.ApiResponse;
import com.city.traffic.toll.fee.calculator.common.model.dto.PaginationDto;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.AddFreeDayRequest;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;


import java.util.List;

public interface FreeDayService {
    ApiResponse<FreeDayResponse> add(AddFreeDayRequest addFreeDayRequest);

    ApiResponse<FreeDayResponse> delete(Long id);

    ApiResponse<PaginationDto<List<FreeDayResponse>>> list(int offset, int limit);
}
