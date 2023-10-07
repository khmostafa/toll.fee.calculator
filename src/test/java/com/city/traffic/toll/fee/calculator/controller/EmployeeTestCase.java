package com.city.traffic.toll.fee.calculator.controller;

import com.city.traffic.toll.fee.calculator.StaticDtoProvider;
import com.city.traffic.toll.fee.calculator.StaticEntityProvider;
import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.model.payload.request.EmployeePayload;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.nio.charset.Charset;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeTestCase {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String EMPLOYEE_URL = "/api/v1/employee";

    @Test
    void employeeAddLowerRoleOne() throws Exception{
        MvcResult result = employeeOneAddEmployeeTwo("eng.khmostafa@gmail.com", EmployeeRole.SUPER_ADMIN,
                "Employee1","fatma@gmail.com", "Image", EmployeeRole.ADMIN);
        asserOK(result);
    }

    @Test
    void employeeAddHigherRoleOne() throws Exception{
        MvcResult result = employeeOneAddEmployeeTwo("eng.khmostafa@gmail.com", EmployeeRole.ADMIN,
                "Employee2","fatma@gmail.com", "Image", EmployeeRole.SUPER_ADMIN);
        assertError(result, HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void notExistedEmployeeAddEmployee() throws Exception{
        EmployeePayload payload = StaticDtoProvider.createEmployeePayload("Fatma Baker", "fata@gmail.com",
                "Profile Image", EmployeeRole.ADMIN);
        assertError(getApiResult("notexisted@email.com", payload), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void addemployeeWithNameNullOrEmpty() throws Exception{
        MvcResult result = employeeOneAddEmployeeTwo("eng.khmostafa@gmail.com", EmployeeRole.SUPER_ADMIN,
                null,"fatma@gmail.com", "Image", EmployeeRole.ADMIN);
        assertError(result, HttpStatus.BAD_REQUEST, ErrorKeys.EMPLOYEE_NAME_MUST_NOT_BE_NULL_OR_EMPTY);

        result = employeeOneAddEmployeeTwo("eng.khmostafa@gmail.com", EmployeeRole.SUPER_ADMIN,
                "","fatma@gmail.com", "Image", EmployeeRole.ADMIN);
        assertError(result, HttpStatus.BAD_REQUEST, ErrorKeys.EMPLOYEE_NAME_MUST_NOT_BE_NULL_OR_EMPTY);
    }

    private MvcResult getApiResult(String email, EmployeePayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(EMPLOYEE_URL)
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private void asserOK(MvcResult result) throws Exception {
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertAll(() -> {
            assertEquals(200, result.getResponse().getStatus());
            assertTrue(json.get("success").asBoolean());
        });
    }

    private void assertError(MvcResult result, HttpStatus status, String message) throws Exception {
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertAll(() -> {
            assertEquals(status.value(), result.getResponse().getStatus());
            assertFalse(json.get("success").asBoolean());
            assertEquals(message, json.get("errors").get("enMessage").asText());
        });
    }


    private MvcResult employeeOneAddEmployeeTwo(String firstEmail, EmployeeRole firstRole,
                String secondName, String secondEmail, String secondImage, EmployeeRole secondRole) throws Exception {
        EmployeeEntity superAdmin = StaticEntityProvider.createEmployee("First Employee", firstEmail,
                "Profile Image", firstRole);
        employeeRepository.save(superAdmin);

        EmployeePayload payload = StaticDtoProvider.createEmployeePayload(secondName, secondEmail,
                secondImage, secondRole);
        return getApiResult(superAdmin.getEmail(), payload);
    }

    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("postgres");

    @DynamicPropertySource
    @SneakyThrows
    public static void setupThings(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgres).join();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.properties.hibernate.default_schema", () -> "public");
        registry.add("eureka.client.enabled", () -> false);
    }
    @BeforeEach
    void clear_database() {
        employeeRepository.deleteAll();
    }

}
