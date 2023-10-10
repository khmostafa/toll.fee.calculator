package com.city.traffic.toll.fee.calculator.startup;

import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class StartUp implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {

        if(Objects.isNull(employeeRepository.findByEmail("eng.khmostafa@gmail.com").orElse(null))){
            employeeRepository.save(
                    EmployeeEntity.builder()
                    .name("First Employee")
                    .email("eng.khmostafa@gmail.com")
                    .role(EmployeeRole.SUPER_ADMIN)
                    .profileImage("Profile Image Path")
                    .build()
            );
        }
    }
}