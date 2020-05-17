package com.it_academyproject.jwt_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class PrivateController
{
	@GetMapping
	public String getMessage()
	{
		return "Hello from private API controller";
	}

	@GetMapping("/LoadExcelFiles")
	public String loadExcelFiles()
	{
		return "Hello from private API controller";
	}

}