package com.it_academyproject.controllers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.it_academyproject.tools.dataImporter.DataImporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("/api/test")
public class testController
{
    @GetMapping
    public String testMSG()
    {
    	
    	try {        
		        FileWriter myWriter = new FileWriter("LOG/log.txt",true);
		        myWriter.write("===================================\n");
		        myWriter.write(new Date()+": TEST API WORKING\n");
		        myWriter.write("===================================\n\n");

		        myWriter.close();

		    } catch (Exception e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	
    	
    	
       return "APP INIT ♫";
       
    }

}
