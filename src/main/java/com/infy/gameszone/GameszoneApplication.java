package com.infy.gameszone;

import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@PropertySource(value = { "classpath:messages.properties" })
public class GameszoneApplication {

	// read properties from application.properties file -> app.config.time-zone
	@Value("${app.config.time-zone:#{null}}")
	private Optional<String> TIME_ZONE;

	// logger
	Logger logger = LoggerFactory.getLogger(GameszoneApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GameszoneApplication.class, args);
	}

	// This method is called when the application is started.
	@PostConstruct
	public void init() {

		// set time zone for the application based on the application configuration file
		// (applicatain.properties - app.config.time-zone)
		if (TIME_ZONE.isPresent() && ZoneId.getAvailableZoneIds().contains(TIME_ZONE.get())) {
			logger.info("Setting application timezone to " + TIME_ZONE.get());
			TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(TIME_ZONE.get())));
		} else {
			logger.info("Setting application timezone to " + "UTC");
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		}

	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			public void addCorsMappings(CorsRegistry corsRegistry) {
				corsRegistry
						.addMapping("/**")
						.allowedOriginPatterns("*")
						.allowCredentials(false)
						.allowedHeaders("Origin", "Content-Type", "Accept", "Authorization", "Accept-Language",
								"connection", "Cache-Control", "Access-Control-Request-Method",
								"Access-Control-Request-Headers")
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
			}
		};
	}
}
