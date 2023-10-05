package com.city.traffic.toll.fee.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.city.traffic.toll.fee.calculator")
public class TollFeeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TollFeeCalculatorApplication.class, args);
	}

}
