package com.it_academyproject.controllers;

//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import com.it_academyproject.tools.dataImporter.DataImporter;
//import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class DataImportController
{
	/*	
	// COMMENTED AFTER B-30 REFACTORING
	
	@Autowired 
	DataImporter dataImporter; 
	private final String SLASH = "\\";
	
	@GetMapping 
	public String getMessage() 
	{ 
		JSONObject errorData = new JSONObject(); 
		Map<Integer , String> teacherVSitinerary = dataImporter.manualCreation();
		dataImporter.importAlumnesActius(); 
		errorData.put("Exercises", dataImporter.importEjerciciosporalumno(0, 1, teacherVSitinerary.get(1))); 
		errorData.put("Exercises", dataImporter.importEjerciciosporalumno(1, 2, teacherVSitinerary.get(2))); 
		errorData.put("Exercises", dataImporter.importEjerciciosporalumno(2, 3, teacherVSitinerary.get(3))); 
		errorData.put("Exercises", dataImporter.importEjerciciosporalumno(3, 4, teacherVSitinerary.get(4))); 
		dataImporter.importTaules();
		return errorData.toString(); 
	}
	*/

}