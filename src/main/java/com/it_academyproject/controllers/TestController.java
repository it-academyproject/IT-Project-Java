package com.it_academyproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileWriter;
import java.util.Date;

@RestController
@RequestMapping("/api/test")
public class TestController
{
	
	// -------------------- -------------------- //
	
	@GetMapping
	public String testMSG()
	{
		try
		{
			FileWriter myWriter = new FileWriter("LOG/log.txt", true);
			myWriter.write("===================================\n");
			myWriter.write(new Date() + ": TEST API WORKING\n");
			myWriter.write("===================================\n\n");
			myWriter.close();
		} 
		catch (Exception e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return "APP INIT ♫";
	}

}