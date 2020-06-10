package com.it_academyproject.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseFromStudentDTO;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseListDTO;
import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.UserExercise;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.Teacher;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Project;
import com.it_academyproject.domains.ProjectItinerary;
import com.it_academyproject.domains.StatusExercise;
import com.it_academyproject.exceptions.BadRoleException;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.ExerciseRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.repositories.StatusExerciseRepository;
import com.it_academyproject.repositories.UserExerciseRepository;
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

import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserExerciseController {
	@Autowired
	UserExerciseService userExerciseService;

	@Autowired
	UserExerciseRepository userExerciseRepository;
	
	@Autowired
	ExerciseRepository exerciseRepository;

	@Autowired
	StatusExerciseRepository statusExerciseRepository;
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	@Autowired
	MyAppUserService userService;

	@PostMapping("/api/exercise")
	public UserExercise createNewUserExercise(@RequestBody UserExercise userExercise) {
		StatusExercise statusExercise = userExercise.getStatus();
		Exercise exercise = userExercise.getExercise();
		Student student = userExercise.getUserStudent();
		Teacher teacher = userExercise.getUserTeacher();
		
		StatusExercise trueStatusExercise = statusExerciseRepository.findById(statusExercise.getId())
				.orElseThrow(() -> new ResourceNotFoundException("status exercise not found"));
		
		Exercise trueExercise = exerciseRepository.findById(exercise.getId())
				.orElseThrow(() -> new ResourceNotFoundException("exercise not found"));
		
		Student trueStudent = (Student) myAppUserRepository.findById(student.getId())
				.orElseThrow(() -> new ResourceNotFoundException("student not found"));
		
		Teacher trueTeacher = (Teacher) myAppUserRepository.findById(teacher.getId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher not found"));

		userExercise.setStatus(trueStatusExercise);
		userExercise.setExercise(trueExercise);
		userExercise.setUserStudent(trueStudent);
		userExercise.setUserTeacher(trueTeacher);

		UserExercise newUserExercise = new UserExercise();

		return userExerciseRepository.save(newUserExercise);		
	}



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

	@GetMapping("/api/exercises/student-id/{id}")
	public List<ExerciseFromStudentDTO> getExercisesByStudentIdNew(@PathVariable(name = "id") String id) {
		try {
			return ExerciseFromStudentDTO.getList(userExerciseService.getExercisesByStudentId(id));
		} catch (UserNotFoundException | BadRoleException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist or it's not a student", e);
		}
	}

	/*
	 * Modelo de llamada PUT: { "id": 1, "status":{"id":4} } La fecha se
	 * actualiza automáticamente desde el back end, no hace falta incorporarla en el
	 * JSON
	 */

	@PutMapping("/api/exercises")
	@ResponseBody
	public UserExercise updateUserExerciseStatus(@RequestBody UserExercise userExercise) {
		return userExerciseService.setUserExerciseStatusData(userExercise);
	}

}
