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
import com.it_academyproject.services.StudentService;
import com.it_academyproject.services.TeacherService;
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
	StudentService studentService;

	@Autowired
	TeacherService teacherService;

	@Autowired
	MyAppUserService userService;

	@PostMapping("/api/exercises")
	public UserExercise newUserExercise(@RequestBody UserExercise UE) {
		StatusExercise statusExercise = UE.getStatus();
		Exercise exercise = UE.getExercise();
		Student student = UE.getUserStudent();

		StatusExercise trueStatusExercise = statusExerciseRepository.findById(statusExercise.getId())
				.orElseThrow(() -> new ResourceNotFoundException("status exercise not found"));
		Exercise trueExercise = exerciseRepository.findById(exercise.getId())
				.orElseThrow(() -> new ResourceNotFoundException("exercise not found"));
		Student trueStudent = studentService.findOneById(student.getId());
		
		UserExercise findUserExercise = userExerciseRepository.findOneByUserStudentAndExercise(trueStudent, trueExercise);
		
		if(findUserExercise!=null) {
			UE.setStatus(trueStatusExercise);
			UE.setExercise(trueExercise);
			UE.setUserStudent(trueStudent);
			UserExercise newUserExercise = new UserExercise(UE);
			return userExerciseRepository.save(newUserExercise);
		} else {
			return null;
		}
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
