
package com.it_academyproject.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseFromStudentDTO;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseListDTO;
<<<<<<< HEAD
import com.it_academyproject.domains.Exercice;
import com.it_academyproject.domains.UserExercice;
=======
import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.UserExercise;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.MyAppUser;
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
import com.it_academyproject.exceptions.BadRoleException;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.UserExerciseRepository;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.services.UserExerciseService;
import com.it_academyproject.tools.View;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserExerciseController {
	@Autowired
	UserExerciseService userExerciseService;

	@Autowired
	UserExerciseRepository userExerciseRepository;

	@Autowired
	MyAppUserService userService;

	@GetMapping("/api/exercises/{itineraryId}")
	public ResponseEntity<String> getExerciseStudentByItinerary(@PathVariable String itineraryId) {
		try {
			JSONObject sendData = userExerciseService.getExerciseStudentByItinerary(itineraryId);
			return new ResponseEntity(sendData.toString(), HttpStatus.FOUND);
		} catch (Exception e) {
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("message", exceptionMessage);
			sendData.put("Message", message);
			return new ResponseEntity(sendData.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/api/exercises")
	public List<ExerciseListDTO> getAllExerciseswithStudents() {
		List<Exercise> foundExercises = userExerciseService.getAllExercises();
		List<ExerciseListDTO> allExerciseswithStudents = new ArrayList<ExerciseListDTO>();

		for (Exercise exercise : foundExercises) {

			List<UserExercise> studentsforThatExercise = userExerciseService.getStudentsByExercise(exercise);
			allExerciseswithStudents.add(new ExerciseListDTO(exercise.getId(), exercise.getName(),
					exercise.getItinerary(), studentsforThatExercise));
		}
		
		return allExerciseswithStudents;
	}

<<<<<<< HEAD
	@GetMapping ("/api/exercises/student-id/{id}")
	public List<ExerciseFromStudentDTO> getExercisesByStudentId(@PathVariable(name="id") String id){
=======
	@GetMapping("/api/exercises/student-id/{id}")
	public List<ExerciseFromStudentDTO> getExercisesByStudentIdNew(@PathVariable(name = "id") String id) {
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
		try {
			return ExerciseFromStudentDTO.getList(userExerciseService.getExercisesByStudentId(id));
		} catch (UserNotFoundException | BadRoleException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist or it's not a student", e);
		}
	}

	/*
	 * Modelo de llamada PUT: { "id": 1, "statusExercise":{"id":4} } La fecha se
	 * actualiza automáticamente desde el back end, no hace falta incorporarla en el
	 * JSON
	 */
	@PutMapping("/api/exercises")
	@ResponseBody
	public UserExercise updateUserExerciseStatus(@RequestBody UserExercise userExercise) {
		return userExerciseService.setUserExerciseStatusData(userExercise);
	}

}
