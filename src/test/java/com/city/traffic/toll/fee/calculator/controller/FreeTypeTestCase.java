package com.city.traffic.toll.fee.calculator.controller;

import com.city.traffic.toll.fee.calculator.StaticDtoProvider;
import com.city.traffic.toll.fee.calculator.StaticEntityProvider;
import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.request.FreeVehiclePayload;
import com.city.traffic.toll.fee.calculator.freevehicle.repository.FreeVehicleRepository;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
class FreeTypeTestCase {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    FreeVehicleRepository freeVehicleRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String EMPLOYEE_URL = "/api/v1/free-vehicle";

    @Test
    void notExistedEmployeeAddFreeVehicle() throws Exception{
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        assertError(getAddResult("notexisted@email.com", payload), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void notAuthorizedEmployeeAddFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.JUST_USER);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void authorizedEmployeeAddFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        assertOK(getAddResult(user.getEmail(), payload));
    }

    @Test
    void authorizedEmployeeAddFreeVehicleWithNullName() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad(null);
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.BAD_REQUEST, ErrorKeys.FREE_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY);
    }

    @Test
    void notExistedEmployeeUpdateFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getUpdateResult("notFound@gmail.com", json.get("payload").get("id").asLong(), payload),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeUpdateFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(getUpdateResult(user.getEmail(), json.get("payload").get("id").asLong(), StaticDtoProvider.createFreeVehiclePayLoad("KIA")));
    }

    @Test
    void notAuthorizedEmployeeUpdateFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        EmployeeEntity notAuthorizedUser = saveEmployee("Khaled1", "end.khmostafa1@gmail.com", EmployeeRole.JUST_USER);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getUpdateResult(notAuthorizedUser.getEmail(), json.get("payload").get("id").asLong(), payload),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void notExistedEmployeeDeleteFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getDeleteResult("notFound@gmail.com", json.get("payload").get("id").asLong()),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeDeleteFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(getDeleteResult(user.getEmail(), json.get("payload").get("id").asLong()));
    }

    @Test
    void notAuthorizedEmployeeDeleteFreeVehicle() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        EmployeeEntity notAuthorizedUser = saveEmployee("Khaled1", "end.khmostafa1@gmail.com", EmployeeRole.JUST_USER);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getDeleteResult(notAuthorizedUser.getEmail(), json.get("payload").get("id").asLong()),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void notExistedEmployeeListFreeVehicles() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        FreeVehiclePayload payload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        assertError(getListResult("notFound@gmail.com"), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeListFreeVehicles() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", EmployeeRole.EMPLOYEE);
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createFreeVehiclePayLoad("MAZDA") ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createFreeVehiclePayLoad("MAZDAA") ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createFreeVehiclePayLoad("MAZDAAA") ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createFreeVehiclePayLoad("MAZDAAAA") ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createFreeVehiclePayLoad("MAZDAAAAAA") ));
        MvcResult result = getListResult(user.getEmail());
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);
        assertEquals(5, json.get("payload").get("totalElements").asLong());
    }

    private EmployeeEntity saveEmployee(String name, String email, EmployeeRole role){
        return employeeRepository.save(StaticEntityProvider.createEmployee(name, email, "Image", role));
    }

    private MvcResult getAddResult(String email, FreeVehiclePayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(EMPLOYEE_URL)
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getUpdateResult(String email, Long id, FreeVehiclePayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(EMPLOYEE_URL + "/" + id)
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getDeleteResult(String email, Long id) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete(EMPLOYEE_URL + "/" + id)
                .header("email", email)
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getListResult(String email) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(EMPLOYEE_URL)
                .header("email", email)
                .contentType("application/json")
                .param("offset", "0")
                .param("limit", "10");

        return mockMvc.perform(builder).andReturn();
    }

    private void assertOK(MvcResult result) throws Exception {
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
        freeVehicleRepository.deleteAll();
    }

}
