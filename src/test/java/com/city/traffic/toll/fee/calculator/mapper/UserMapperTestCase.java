package com.city.traffic.toll.fee.calculator.mapper;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.freedays.model.entity.FreeDayEntity;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.response.FreeDayResponse;
import com.city.traffic.toll.fee.calculator.user.mapper.EmployeeMapper;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.EmployeePayload;
import com.city.traffic.toll.fee.calculator.user.model.payload.response.EmployeeResponse;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMapperTestCase {

    @Autowired
    EmployeeMapper employeeMapper;

    EmployeePayload employeePayload = EmployeePayload.builder()
            .name("Test Employee1")
            .email("test1@email.com")
            .profileImage("Image1")
            .role(EmployeeRole.EMPLOYEE)
            .build();

    EmployeeEntity employeeEntity = EmployeeEntity.builder()
            .name("Test Employee2")
            .email("test2@email.com")
            .profileImage("image2")
            .role(EmployeeRole.EMPLOYEE)
            .build();

    @Test
    void userPayloadToUserEntity(){
        EmployeeEntity employeeEntity1 = employeeMapper.toEntity(employeePayload);
        assertAll(() -> {
            assertEquals(employeePayload.getName(), employeeEntity1.getName());
            assertEquals(employeePayload.getEmail(), employeeEntity1.getEmail());
            assertEquals(employeePayload.getProfileImage(), employeeEntity1.getProfileImage());
            assertEquals(employeePayload.getRole(), employeeEntity1.getRole());
        });
    }


    @Test
    void userEntityToUserResponse(){
        EmployeeResponse employeeResponse = employeeMapper.toResponse(employeeEntity);
        assertAll(() -> {
            assertEquals(employeeEntity.getName(), employeeResponse.getName());
            assertEquals(employeeEntity.getEmail(), employeeResponse.getEmail());
            assertEquals(employeeEntity.getProfileImage(), employeeResponse.getProfileImage());
            assertEquals(employeeEntity.getRole(), employeeResponse.getRole());
        });
    }

    @Test
    void userEntityUpdateFromUserPayload(){
        EmployeeEntity employeeEntity1 = employeeMapper.updateEntity(employeePayload, employeeEntity);
        assertAll(() -> {
            assertEquals(employeePayload.getName(), employeeEntity1.getName());
            assertEquals(employeePayload.getEmail(), employeeEntity1.getEmail());
            assertEquals(employeePayload.getProfileImage(), employeeEntity1.getProfileImage());
            assertEquals(employeePayload.getRole(), employeeEntity1.getRole());
        });
    }


}
