
package com.it_academyproject.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercise;
import com.it_academyproject.repositories.UserExerciseRepository;
import com.it_academyproject.services.UserExerciseService;
import com.it_academyproject.tools.View;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserExerciseController
{
	@Autowired
	UserExerciseService userExerciseService;

	@Autowired
	UserExerciseRepository userExerciseRepository;

	@GetMapping ("/api/userExercise/{itineraryId}")
	public ResponseEntity<String>  getExerciseStudentByItinerary (@PathVariable String itineraryId )
	{
		try
		{
			JSONObject sendData = userExerciseService.getExerciseStudentByItinerary( itineraryId );
			return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
		}
		catch (Exception e)
		{
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type" , "error");
			message.put("message" , exceptionMessage);
			sendData.put("Message" , message);
			return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping ("/api/userExercise")
	public ResponseEntity  getExerciseStudentByItinerary ( )
	{
		try
		{
			//get a list of all the itnerarys

			JSONObject sendData = userExerciseService.getExerciseStudentByItinerary( );
			return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
		}
		catch (Exception e)
		{
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type" , "error");
			message.put("message" , exceptionMessage);
			sendData.put("Message" , message);
			return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
		}
	}

	@JsonView(View.Summary.class)
	@GetMapping ("/api/userExercise/Student_id")
	public ResponseEntity<String> getExercisesByStudentId(@RequestBody MyAppUser student){

		try
		{
			JSONObject sendData = userExerciseService.getExerciseStudentByStudent(student);
			return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
		}
		catch (Exception e)
		{
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type" , "error");
			message.put("message" , exceptionMessage);
			sendData.put("Message" , message);
			return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
		}
	}



	/*
	 * Modelo de llamada PUT: { "id": 1, "statusExercise":{"id":4} }
	 * La fecha se actualiza automáticamente desde el back end, 
	 * no hace falta incorporarla en el JSON
	 */
	//SET @CrossOrigin BEFORE DEPLOYING TO PRODUCTION!
	@CrossOrigin
	@JsonView(View.Summary.class)
	@PutMapping("/api/userExercise/exercice_id")
	public boolean setUserExerciseStatusData(@RequestBody UserExercise userExercise) {

		return userExerciseService.setUserExerciseStatusData(userExercise);
		
	}
}

