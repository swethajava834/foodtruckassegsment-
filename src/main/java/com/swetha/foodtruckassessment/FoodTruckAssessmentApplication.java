package com.swetha.foodtruckassessment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Food Truck Assessment API", version = "1.0",
		description = "Food Truck Assessment API"))
public class FoodTruckAssessmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodTruckAssessmentApplication.class, args);
	}
}
