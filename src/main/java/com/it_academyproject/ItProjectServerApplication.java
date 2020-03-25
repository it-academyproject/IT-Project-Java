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

	public static void main(String[] args) {
		SpringApplication.run(ItProjectServerApplication.class, args);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").
						allowedOrigins("*").
						allowedMethods("*");
			}
		};
	}

}
	
	

