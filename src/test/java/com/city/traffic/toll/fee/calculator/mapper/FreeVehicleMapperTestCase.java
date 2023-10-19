package com.city.traffic.toll.fee.calculator.mapper;

import com.city.traffic.toll.fee.calculator.freevehicle.mapper.FreeVehicleMapper;
import com.city.traffic.toll.fee.calculator.freevehicle.model.entity.FreeVehicleEntity;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.request.FreeVehiclePayload;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.response.FreeVehicleResponse;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FreeVehicleMapperTestCase {
    @Autowired
    FreeVehicleMapper freeVehicleMapper;
    FreeVehicleEntity freeVehicleEntity = FreeVehicleEntity.builder()
            .type("KIA")
            .build();
    FreeVehiclePayload freeVehiclePayload = FreeVehiclePayload.builder()
            .type("KIA2")
            .build();

    @Test
    void freeVehiclePayloadToFreeVehicleEntity(){
        FreeVehicleEntity freeVehicleEntity = freeVehicleMapper.toEntity(freeVehiclePayload);
        assertAll(() -> assertEquals(freeVehiclePayload.getType(), freeVehicleEntity.getType()));
    }


    @Test
    void freeVehicleEntityToFreeVehicleResponse(){
        FreeVehicleResponse freeVehicleResponse = freeVehicleMapper.toResponse(freeVehicleEntity);
        assertAll(() -> assertEquals(freeVehicleEntity.getType(), freeVehicleResponse.getType()));
    }

    @Test
    void freeVehicleEntityUpdateFromFreeVehiclePayload(){
        FreeVehicleEntity updatedEntity = freeVehicleMapper.updateEntity(freeVehiclePayload, freeVehicleEntity);
        assertAll(() -> assertEquals(freeVehiclePayload.getType(), updatedEntity.getType()));
    }
}
