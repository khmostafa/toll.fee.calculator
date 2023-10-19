package com.city.traffic.toll.fee.calculator.mapper;

import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.FreeDayPayload;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.toll.mapper.TollMapper;
import com.city.traffic.toll.fee.calculator.toll.model.entity.TollEntity;
import com.city.traffic.toll.fee.calculator.toll.model.payload.request.TollPayload;
import com.city.traffic.toll.fee.calculator.toll.model.payload.response.TollResponse;
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
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TollMapperTestCase {

    @Autowired
    TollMapper tollMapper;

    TollPayload tollPayload = TollPayload.builder()
            .vehicleNo("ESD-1234")
            .type("KIA")
            .date(LocalDateTime.of(2023, 10, 12, 11, 30))
            .build();

    TollEntity tollEntity = TollEntity.builder()
            .vehicleNo("ESD-12345")
            .type("KIA2")
            .date(LocalDateTime.of(2023, 10, 12, 11, 30))
            .build();

    @Test
    void tollPayloadToTollEntity(){
        TollEntity tollEntity = tollMapper.toEntity(tollPayload);
        assertAll(() -> {
            assertEquals(tollPayload.getVehicleNo(), tollEntity.getVehicleNo());
            assertEquals(tollPayload.getType(), tollEntity.getType());
            assertEquals(tollPayload.getDate(), tollEntity.getDate());
        });
    }


    @Test
    void tollEntityToTollResponse(){
        TollResponse tollResponse = tollMapper.toResponse(tollEntity);
        assertAll(() -> {
            assertEquals(tollEntity.getVehicleNo(), tollResponse.getVehicleNo());
            assertEquals(tollEntity.getType(), tollResponse.getType());
            assertEquals(tollEntity.getDate(), tollResponse.getDate());
        });
    }

    @Test
    void tollEntityUpdateFromTollPayload(){
        TollEntity updatedEntity = tollMapper.updateEntity(tollPayload, tollEntity);
        assertAll(() -> {
            assertEquals(tollPayload.getVehicleNo(), updatedEntity.getVehicleNo());
            assertEquals(tollPayload.getType(), updatedEntity.getType());
            assertEquals(tollPayload.getDate(), updatedEntity.getDate());
        });
    }


}
