package com.it_academyproject;

import com.it_academyproject.jwt_security.configuration.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@ComponentScan(basePackages={"com.it_academyproject" , "com.it_academyproject.controllers", "com.it_academyproject.jwt_security"})
@EntityScan(basePackages = {"com.it_academyproject.domains" , "com.it_academyproject.jwt_security.model"} )
@EnableJpaRepositories(basePackages = {"com.it_academyproject.repositories" , "com.it_academyproject.jwt_security.repository"})
@EnableWebMvc
@Configuration
@EnableScheduling
@Import(SecurityConfiguration.class)
public class ItProjectServerApplication {

	// ------------------------------ CORS CONFIGURATION CONSTANTS -------------------------

	// Admin options used in controllers

	// Allowed requests for standard_origins
	public static final String[] STANDARD_METHODS = {"GET", "HEAD", "PUT"};

	// Wildcards but "*" not allowed (https://www.baeldung.com/spring-cors#comment-11065)
	// Allowed origins to standard_methods
	public static final String[] STANDARD_ORIGIN = {"*"};

	// Allowed origins for modifier requests (PUT, DELETE, POST)
	// Currently allowing all of the origins. Change "*" by allowed origin to limit origin
	// NOTE: if you allow "*" globally, you cannot limit PUT locally
	public static final String ADMIN_ORIGIN = "*";
	public static final String ADMIN_ORIGIN2 = "*";
	public static final String ADMIN_ORIGIN3 = "*";

	// If you need to add more allowed origins:
	// 1. Create new origin String here (use of String[] not allowed)
	// 2. Import the string in the corresponding controller file
	// 3. Change annotation in controller method
	// Example:
	// New origin (in this file):
	// 		public static final String ADMIN_ORIGIN2 = "https://217.23.54.234:6570";
	// New annotation (in affected method of affected controller):
	// 		@CrossOrigin ( origins = {ADMIN_ORIGIN, ADMIN_ORIGIN2})
	//
	// Remember:
	//	- No wildcards allowed (*, %...)
	//	- No port specified implies port 80



	// ------------------------------ CORS CONFIGURATION CONSTANTS END -------------------------


	public static void main(String[] args) {
		SpringApplication.run(ItProjectServerApplication.class, args);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				// CORS standard access mapping
				registry.addMapping("/**").
						allowedOrigins(STANDARD_ORIGIN).
						allowedMethods(STANDARD_METHODS);
			}
		};
	}
}