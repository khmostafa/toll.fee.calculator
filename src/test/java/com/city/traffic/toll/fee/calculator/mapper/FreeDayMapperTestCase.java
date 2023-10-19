package com.city.traffic.toll.fee.calculator.mapper;

import com.city.traffic.toll.fee.calculator.freedays.mapper.FreeDayMapper;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.FreeDayPayload;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
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
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FreeDayMapperTestCase {

    @Autowired
    FreeDayMapper freeDayMapper;

    FreeDayPayload freeDayPayload = FreeDayPayload.builder()
            .name("Test Name")
            .freeDay(LocalDate.of(2023, 9, 12))
            .build();

    FreeDayEntity freeDayEntity = FreeDayEntity.builder()
            .name("Test Name2")
            .freeDay(LocalDate.of(2023, 9, 12))
            .build();

    @Test
    void freeDayPayloadToFreeDayEntity(){
        FreeDayEntity freeDayEntity = freeDayMapper.toEntity(freeDayPayload);
        assertAll(() -> {
            assertEquals(freeDayPayload.getName(), freeDayEntity.getName());
            assertEquals(freeDayPayload.getFreeDay(), freeDayEntity.getFreeDay());
        });
    }


    @Test
    void freeDayEntityToFreeDayResponse(){
        FreeDayResponse freeDayResponse = freeDayMapper.toResponse(freeDayEntity);
        assertAll(() -> {
            assertEquals(freeDayEntity.getName(), freeDayResponse.getName());
            assertEquals(freeDayEntity.getFreeDay(), freeDayResponse.getFreeDay());
        });
    }

    @Test
    void freeDayEntityUpdateFromFreeDayPayload(){
        FreeDayEntity updatedEntity = freeDayMapper.updateEntity(freeDayPayload, freeDayEntity);
        assertAll(() -> {
            assertEquals(freeDayPayload.getName(), updatedEntity.getName());
            assertEquals(freeDayPayload.getFreeDay(), updatedEntity.getFreeDay());
        });
    }


}
