package com.city.traffic.toll.fee.calculator.controller;

import com.city.traffic.toll.fee.calculator.StaticDtoProvider;
import com.city.traffic.toll.fee.calculator.StaticEntityProvider;
import com.city.traffic.toll.fee.calculator.common.exception.ErrorKeys;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.freedays.model.payload.request.FreeDayPayload;
import com.city.traffic.toll.fee.calculator.freedays.repository.FreeDayRepository;
import com.city.traffic.toll.fee.calculator.freevehicle.model.payload.request.FreeVehiclePayload;
import com.city.traffic.toll.fee.calculator.freevehicle.repository.FreeVehicleRepository;
import com.city.traffic.toll.fee.calculator.hourfee.model.payload.request.HourFeePayload;
import com.city.traffic.toll.fee.calculator.hourfee.repository.HourFeeRepository;
import com.city.traffic.toll.fee.calculator.toll.model.payload.request.TollPayload;
import com.city.traffic.toll.fee.calculator.toll.repository.TollRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
class TollFeeTestCase {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    FreeDayRepository freeDayRepository;

    @Autowired
    FreeVehicleRepository freeVehicleRepository;

    @Autowired
    HourFeeRepository hourFeeRepository;

    @Autowired
    TollRepository tollRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;



    private final String EMPLOYEE_URL = "/api/v1/toll";

    @Test
    void notExistedEmployeeAddToll() throws Exception{
        TollPayload payload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 10, 15));
        assertError(getAddResult("notexisted@email.com", payload), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void notAuthorizedEmployeeAddToll() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.JUST_USER);
        TollPayload payload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 10, 15));
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_NOT_AUTHORIZED_FOR_THIS_ACTION);
    }

    @Test
    void authorizedEmployeeAddToll() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);
        TollPayload payload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 10, 15));
        assertOK(getAddResult(user.getEmail(), payload));
    }

    @Test
    void authorizedEmployeeAddTollWithNullVehicleNo() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);
        TollPayload payload = StaticDtoProvider.createTollPayLoad(null, "MAZDA",
                LocalDateTime.of(2023, 10, 2, 10, 15));
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.BAD_REQUEST, ErrorKeys.TOLL_VEHICLE_NO_MUST_NOT_BE_NULL_OR_EMPTY);
    }

    @Test
    void authorizedEmployeeAddTollWithNullType() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);
        TollPayload payload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", null,
                LocalDateTime.of(2023, 10, 2, 10, 15));
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.BAD_REQUEST, ErrorKeys.TOLL_VEHICLE_TYPE_MUST_NOT_BE_NULL_OR_EMPTY);
    }

    @Test
    void authorizedEmployeeAddTollWithNullTime() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);
        TollPayload payload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                null);
        assertError(getAddResult(user.getEmail(), payload), HttpStatus.BAD_REQUEST, ErrorKeys.TOLL_DAY_MUST_NOT_BE_NULL);
    }

    @Test
    void notExistedEmployeeListTollsForVehicle() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);
        TollPayload payload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 10, 15));
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        assertError(getListResult("notFound@gmail.com", "KSA_ABC1234"), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeListTolls() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createTollPayLoad("KSA_ABC1", "MAZDA",
                        LocalDateTime.of(2023, 10, 2, 10, 15)) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createTollPayLoad("KSA_ABC1", "MAZDA",
                        LocalDateTime.of(2023, 10, 2, 10, 15)) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createTollPayLoad("KSA_ABC1", "MAZDA",
                        LocalDateTime.of(2023, 10, 2, 10, 15)) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createTollPayLoad("KSA_ABC4", "MAZDA",
                        LocalDateTime.of(2023, 10, 2, 10, 15)) ));
        assertOK(getAddResult(user.getEmail(),
                StaticDtoProvider.createTollPayLoad("KSA_ABC4", "MAZDA",
                        LocalDateTime.of(2023, 10, 2, 10, 15)) ));
        MvcResult result = getListResult(user.getEmail(), "KSA_ABC1");
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);
        assertEquals(3, json.get("payload").get("totalElements").asLong());

        result = getListResult(user.getEmail(), "KSA_ABC4");
        json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);
        assertEquals(2, json.get("payload").get("totalElements").asLong());

        result = getListResult(user.getEmail(), "NotFoundVehicle");
        json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);
        assertEquals(0, json.get("payload").get("totalElements").asLong());
    }


    @Test
    void notExistedEmployeeListFeeForVehicle() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);
        TollPayload payload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 10, 15));
        MvcResult addResult = getAddResult(user.getEmail(), payload);
        assertOK(addResult);
        assertError(getFeeReport("notFound@gmail.com"), HttpStatus.FORBIDDEN, ErrorKeys.THIS_USER_IS_NOT_FOUND_IN_OUR_SYSTEM);
    }

    @Test
    void authorizedEmployeeCalculateFeeForVehicleWithFreeType() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);

        FreeVehiclePayload freeVehiclePayload = StaticDtoProvider.createFreeVehiclePayLoad("MAZDA");
        assertOK(getAddResult(user.getEmail(), freeVehiclePayload));

        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        assertOK(getAddResult(user.getEmail(), payload));
        
        TollPayload tollPayload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 11, 15));
        assertOK(getAddResult(user.getEmail(), tollPayload));

        MvcResult result = getFeeReport(user.getEmail());
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);

        assertEquals(0L, json.get("payload").get("data").size());
        
    }

    @Test
    void authorizedEmployeeCalculateFeeForVehicleWithFreeDay() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);

        FreeDayPayload freeDayPayload = StaticDtoProvider.createFreeDayPayLoad("October Victory", LocalDate.of(2023, 10, 2));
        assertOK(getAddResult(user.getEmail(), freeDayPayload));

        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        assertOK(getAddResult(user.getEmail(), payload));

        TollPayload tollPayload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 11, 15));
        assertOK(getAddResult(user.getEmail(), tollPayload));

        MvcResult result = getFeeReport(user.getEmail());
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);

        assertEquals(0L, json.get("payload").get("data").size());

    }

    @Test
    void authorizedEmployeeCalculateFeeForVehicleWithoutFreeDayAndWithoutFreeType() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);

        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(11, 30), 18L);
        assertOK(getAddResult(user.getEmail(), payload));

        TollPayload tollPayload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 11, 15));
        assertOK(getAddResult(user.getEmail(), tollPayload));

        MvcResult result = getFeeReport(user.getEmail());
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);

        assertNotEquals(0L, json.get("payload").get("data").size());
        assertEquals(18L, json.get("payload").get("data").get(0).get("fee").asLong());

    }

    @Test
    void authorizedEmployeeCalculateFeeForVehicleWithTwoFeesWithinHourAndGetHighestValue() throws Exception{
        EmployeeEntity user = saveEmployee(EmployeeRole.EMPLOYEE);

        HourFeePayload payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(10, 30), LocalTime.of(10, 50), 10L);
        assertOK(getAddResult(user.getEmail(), payload));

        payload = StaticDtoProvider.createHourFeePayLoad(LocalTime.of(11, 0), LocalTime.of(11, 30), 18L);
        assertOK(getAddResult(user.getEmail(), payload));

        TollPayload tollPayload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 10, 45));
        assertOK(getAddResult(user.getEmail(), tollPayload));

        tollPayload = StaticDtoProvider.createTollPayLoad("KSA_ABC1234", "MAZDA",
                LocalDateTime.of(2023, 10, 2, 11, 15));
        assertOK(getAddResult(user.getEmail(), tollPayload));

        MvcResult result = getFeeReport(user.getEmail());
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        assertOK(result);

        assertNotEquals(0L, json.get("payload").get("data").size());
        assertEquals(18L, json.get("payload").get("data").get(0).get("fee").asLong());

    }

    private EmployeeEntity saveEmployee(EmployeeRole role){
        return employeeRepository.save(StaticEntityProvider.createEmployee("Khaled", "end.khmostafa@gmail.com", "Image", role));
    }

    private MvcResult getAddResult(String email, TollPayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(EMPLOYEE_URL)
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getAddResult(String email, FreeDayPayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/free-day")
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getAddResult(String email, FreeVehiclePayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/free-vehicle")
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getAddResult(String email, HourFeePayload request) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/hour-fee")
                .header("email", email)
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");
        return mockMvc.perform(builder).andReturn();
    }


    private MvcResult getListResult(String email, String vehicle) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(EMPLOYEE_URL + "/history/" + vehicle)
                .header("email", email)
                .contentType("application/json")
                .param("offset", "0")
                .param("limit", "10");

        return mockMvc.perform(builder).andReturn();
    }

    private MvcResult getFeeReport(String email) throws Exception{
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(EMPLOYEE_URL + "/fee/" + "KSA_ABC1234")
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
        tollRepository.deleteAll();
        hourFeeRepository.deleteAll();
        freeDayRepository.deleteAll();
        freeVehicleRepository.deleteAll();
    }

}
