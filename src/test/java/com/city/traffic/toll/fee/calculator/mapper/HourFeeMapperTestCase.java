package com.city.traffic.toll.fee.calculator.mapper;

import com.city.traffic.toll.fee.calculator.hourfee.mapper.HourFeeMapper;
import com.city.traffic.toll.fee.calculator.hourfee.model.entity.HourFeeEntity;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.response.HourFeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HourFeeMapperTestCase {

    @Autowired
    HourFeeMapper hourFeeMapper;

    HourFeePayload hourFeePayload = HourFeePayload.builder()
            .startTime(LocalTime.of(11, 0))
            .endTime(LocalTime.of(11, 30))
            .value(18L)
            .build();

    HourFeeEntity hourFeeEntity = HourFeeEntity.builder()
            .startTime(LocalTime.of(11, 0))
            .endTime(LocalTime.of(11, 30))
            .value(10L)
            .build();

    @Test
    void hourFeePayloadToHourFeeEntity(){
        HourFeeEntity hourFeeEntity = hourFeeMapper.toEntity(hourFeePayload);
        assertAll(() -> {
            assertEquals(hourFeePayload.getStartTime(), hourFeeEntity.getStartTime());
            assertEquals(hourFeePayload.getEndTime(), hourFeeEntity.getEndTime());
            assertEquals(hourFeePayload.getValue(), hourFeeEntity.getValue());
        });
    }


    @Test
    void hourFeeEntityToHourFeeResponse(){
        HourFeeResponse hourFeeResponse = hourFeeMapper.toResponse(hourFeeEntity);
        assertAll(() -> {
            assertEquals(hourFeeEntity.getStartTime(), hourFeeResponse.getStartTime());
            assertEquals(hourFeeEntity.getEndTime(), hourFeeResponse.getEndTime());
            assertEquals(hourFeeEntity.getValue(), hourFeeResponse.getValue());
        });
    }

    @Test
    void hourFeeEntityUpdateFromHourFeePayload(){
        HourFeeEntity updatedEntity = hourFeeMapper.updateEntity(hourFeePayload, hourFeeEntity);
        assertAll(() -> {
            assertEquals(hourFeePayload.getStartTime(), updatedEntity.getStartTime());
            assertEquals(hourFeePayload.getEndTime(), updatedEntity.getEndTime());
            assertEquals(hourFeePayload.getValue(), updatedEntity.getValue());
        });
    }


}
