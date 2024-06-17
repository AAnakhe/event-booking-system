package com.ajavacode.dev.eventbookingapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(name = "MusalaSoft", email = "qinshifthiringteam@musalasoft.com"),
				title = "Event Booking API.",
				version = "1.0.0"
		),
		servers = {
				@Server(description = "localhost", url = "http://localhost:8080/"),
		}
)
@SecurityScheme(
		name = "bearerAuth",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
@EnableScheduling
@SpringBootApplication()
@ConfigurationPropertiesScan
public class EventBookingappApplication {

	public static void main(String[] args)  {
		SpringApplication.run(EventBookingappApplication.class, args);
	}
}

