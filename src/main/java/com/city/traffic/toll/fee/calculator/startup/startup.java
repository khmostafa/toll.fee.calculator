package com.city.traffic.toll.fee.calculator.startup;

import com.city.traffic.toll.fee.calculator.user.model.entity.EmployeeEntity;
import com.city.traffic.toll.fee.calculator.common.model.enums.EmployeeRole;
import com.city.traffic.toll.fee.calculator.user.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class startup implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public startup(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Add records to the database during application startup
//        EmployeeEntity superAdmin = employeeRepository.findByEmail("eng.khmostafa@gmail.com").orElse(null);
//        if(superAdmin == null){
//            employeeRepository.save(EmployeeEntity.builder()
//                    .name("Khaled Mostafa")
//                    .email("eng.khmostafa@gmail.com")
//                    .profileImage("Image")
//                    .role(EmployeeRole.SUPER_ADMIN)
//                    .build(), 1L) ;
//        }
    }
}