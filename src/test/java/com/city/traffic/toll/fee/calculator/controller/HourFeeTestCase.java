package com.city.traffic.toll.fee.calculator.controller;

import com.city.traffic.toll.fee.calculator.StaticDtoProvider;
import com.city.traffic.toll.fee.calculator.StaticEntityProvider;
import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.hourfee.repository.HourFeeRepository;
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
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HourFeeTestCase {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    HourFeeRepository hourFeeRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String EMPLOYEE_URL = "/api/v1/hour-fee";

    @Test
    void notExistedEmployeeAddHourFee() throws Exception{
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        assertError(getAddResult("notexisted@email.com", payload), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void notAuthorizedEmployeeAddHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.JUST_USER);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void authorizedEmployeeAddHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        assertOK(getAddResult(user.getEmail(), payload));
    }

    @Test
    void authorizedEmployeeAddHourFeeWithNullStartTime() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(null, LocalTime.of(10, 30), 18L);
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.BAD_REQUEST, ErrorKeys.HOUR_FEE_START_TIME_MUST_NOT_BE_NULL);
    }
    @Test
    void authorizedEmployeeAddHourFeeWithNullEndTime() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), null, 18L);
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.BAD_REQUEST, ErrorKeys.HOUR_FEE_END_TIME_MUST_NOT_BE_NULL);
    }

    @Test
    void authorizedEmployeeAddHourFeeWithNullValue() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), null);
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.BAD_REQUEST, ErrorKeys.HOUR_FEE_VALUE_TIME_MUST_NOT_BE_NULL);
    }

    @Test
    void notExistedEmployeeUpdateHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getUpdateResult("notFound@gmail.com", json.get("payload").get("id").asLong(), payload),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeUpdateHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(getUpdateResult(user.getEmail(), json.get("payload").get("id").asLong(),
                StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 25), LocalTime.of(10, 30), 18L)));
    }

    @Test
    void notAuthorizedEmployeeUpdateHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        EmployeeEntity notAuthorizedUser = saveEmployee("Khaled1", "end.khmostafa1@gmail.com", "Image", EmployeeRole.JUST_USER);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getUpdateResult(notAuthorizedUser.getEmail(), json.get("payload").get("id").asLong(), payload),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void notExistedEmployeeDeleteHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getDeleteResult("notFound@gmail.com", json.get("payload").get("id").asLong()),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeDeleteHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(getDeleteResult(user.getEmail(), json.get("payload").get("id").asLong()));
    }

    @Test
    void notAuthorizedEmployeeDeleteHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        EmployeeEntity notAuthorizedUser = saveEmployee("Khaled1", "end.khmostafa1@gmail.com", "Image", EmployeeRole.JUST_USER);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getDeleteResult(notAuthorizedUser.getEmail(), json.get("payload").get("id").asLong()),
                HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void notExistedEmployeeListHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        JsonNode json = objectMapper.readTree(addResult.getResponse().getContentAsString(Charset.defaultCharset()));
        assertError(getListResult("notFound@gmail.com"), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeListHourFee() throws Exception{
        EmployeeEntity user = saveEmployee("Khaled", "end.khmostafa@gmail.com", "Image", EmployeeRole.EMPLOYEE);
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L) ));
        MvcResult result = getListResult(user.getEmail());
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);
        assertEquals(json.get("payload").get("totalElements").asLong(),  5);
    }

    private EmployeeEntity saveEmployee(String name, String email, String image, EmployeeRole role){
        return employeeRepository.save(StaticEntityProvider.createEmployee(name, email, image, role));
    }

    private MvcResult getAddResult(String email, HourFeePayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(EMPLOYEE_URL)
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getUpdateResult(String email, Long id, HourFeePayload request) throws Exception{
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
        hourFeeRepository.deleteAll();
    }

}
