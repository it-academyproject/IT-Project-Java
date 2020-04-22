
package com.it_academyproject.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseFromStudentDTO;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseListDTO;
import com.it_academyproject.domains.Exercice;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercice;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.UserExerciceRepository;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.services.UserExerciseService;
import com.it_academyproject.tools.View;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserExerciseController
{
	@Autowired
	UserExerciseService userExerciseService;

	@Autowired
	UserExerciceRepository userExerciceRepository;

	@Autowired
	MyAppUserService userService;

	@GetMapping ("/api/exercises/{itineraryId}")
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
	
	
	@GetMapping ("/api/exercises")
	public Map<Integer,ExerciseListDTO> getAllExerciseswithStudents ( )
	{
		List<Exercice> foundExercises = userExerciseService.getAllExercises();
		Map<Integer,ExerciseListDTO> allExerciseswithStudents = new HashMap<Integer,ExerciseListDTO>();
		
		for (Exercice exercise : foundExercises)
		{
		
			List<UserExercice> studentsforThatExercise= userExerciseService.getStudentsByExercise(exercise);
			allExerciseswithStudents.put(exercise.getId(), new ExerciseListDTO(exercise.getName(),exercise.getItinerary(),studentsforThatExercise));
		}
		

	return allExerciseswithStudents;
	}
	

	@JsonView(View.Summary.class)
	@GetMapping ("/api/exercises/Student_id")
	public ResponseEntity<String>  getExercicesbyStudentId (@RequestBody MyAppUser student){

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);

		try
		{
			JSONObject sendData = userExerciseService.getExerciseStudentByStudent(userService.getStudentById(student.getId())
					.orElseThrow(()-> new UserNotFoundException("User not found: " + student.getId())));
			return new ResponseEntity( sendData.toString() , responseHeaders, HttpStatus.FOUND);
		}
		catch (Exception e)
		{
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type" , "error");
			message.put("message" , exceptionMessage);
			sendData.put("Message" , message);
			return new ResponseEntity( sendData.toString() , responseHeaders,
					(e.getClass()==UserNotFoundException.class)?HttpStatus.NOT_FOUND:HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping ("/api/exercises/student_id-new/{id}")
	public List<ExerciseFromStudentDTO> getExercisesByStudentIdNew (@PathVariable(name="id") String id){
		return ExerciseFromStudentDTO.getList(userExerciseService.getExercisesByStudentId(id));
	}
	/*public Map<String, List<UserExercice>>  getExercisesByStudentIdNew (@PathVariable(name="id") String id){
		Map<String, List<UserExercice>> response = new HashMap<>();
		response.put(id, userExerciseService.getExercisesByStudentId(id));
		return response;
	}*/

	@JsonView(View.Summary.class)
	@GetMapping ("/api/exercises/student_id/{id}")
	public ResponseEntity<String>  getExercisesByStudentId (@PathVariable(name="id") String id){

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);

		try
		{
			JSONObject sendData = userExerciseService.getExerciseStudentByStudent(userService.getStudentById(id)
					.orElseThrow(()-> new UserNotFoundException("User not found: " + id)));
			return new ResponseEntity( sendData.toString(), responseHeaders, HttpStatus.OK);
		}
		catch (Exception e)
		{
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type" , "error");
			message.put("message" , exceptionMessage);
			sendData.put("Message" , message);
			return new ResponseEntity( sendData.toString(), responseHeaders,
					(e.getClass()==UserNotFoundException.class)?HttpStatus.NOT_FOUND:HttpStatus.BAD_REQUEST);
		}
	}



	/*
	 * Modelo de llamada PUT: { "id": 1, "statusExercice":{"id":4} }
	 * La fecha se actualiza automáticamente desde el back end, 
	 * no hace falta incorporarla en el JSON
	 */
	//SET @CrossOrigin BEFORE DEPLOYING TO PRODUCTION!
	@CrossOrigin
	@JsonView(View.Summary.class)
	@PutMapping("/api/exercises/exercice_id")
	public boolean setUserExerciseStatusData(@RequestBody UserExercice userExercice) { 

		return userExerciseService.setUserExerciseStatusData(userExercice);
		
	}
	


}

