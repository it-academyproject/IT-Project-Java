package com.it_academyproject.services;

import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsExerciseNotTurnedInI;
import com.it_academyproject.domains.*;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.UserExerciseRepository;
import com.it_academyproject.repositories.ExerciseRepository;
import com.it_academyproject.ApplicationConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserExerciseService {
	@Autowired
	UserExerciseRepository userExerciseRepository;
	@Autowired
	MyAppUserService userService;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ItineraryRepository itineraryRepository;
	@Autowired
	ExerciseRepository exerciseRepository;

	public JSONObject getExerciseStudentByItinerary(String itineraryIdString) {
		try {
			int itineraryId = Integer.parseInt(itineraryIdString);
			Itinerary itinerary = itineraryRepository.findOneById(itineraryId);
			List<Course> courseList = courseRepository.findByItineraryAndEndDate(itinerary, null);
			List<Student> students = new ArrayList<>();
			Map<String, List<UserExercise>> userUserExerciseMap = new HashMap<>();

			for (int i = 0; i < courseList.size(); i++) {
				Student student = courseList.get(i).getUserStudent();
				students.add(student);
				List<UserExercise> userExerciseList = userExerciseRepository.findByUserStudent(student);
				// remove the Student field that is not necessary
				for (int j = 0; j < userExerciseList.size(); j++) {
					userExerciseList.get(j).setUserStudent(null);
				}
				userUserExerciseMap.put(student.getId(), userExerciseList);
			}

			JSONObject sendData = new JSONObject(userUserExerciseMap);
			return sendData;
		} catch (Exception e) {
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("message", exceptionMessage);
			sendData.put("Message", message);
			return sendData;
		}
	}

	public List<Exercise> getAllExercises() {
		List<Exercise> allExercises = exerciseRepository.findAll();
		return allExercises;
	}

	public JSONObject getExerciseStudentByStudent(Student student) {
		try {

			// get only active students
			List<Course> courseList = courseRepository.findByUserStudent(student);
			List<Student> studentList = new ArrayList<>();
			Map<String, List<UserExercise>> userUserExerciseMap = new HashMap<>();
			for (int i = 0; i < courseList.size(); i++) {
				Student myAppUser = courseList.get(i).getUserStudent();
				studentList.add(myAppUser);
				List<UserExercise> userExerciseList = userExerciseRepository.findByUserStudent(myAppUser);
				// remove the Student field that is not necessary
				for (int j = 0; j < userExerciseList.size(); j++) {
					userExerciseList.get(j).setUserStudent(null);
				}
				userUserExerciseMap.put(myAppUser.getId(), userExerciseList);
			}
			JSONObject sendData = new JSONObject(userUserExerciseMap);
			return sendData;
		} catch (Exception e) {
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("message", exceptionMessage);
			sendData.put("Message", message);
			return sendData;
		}
	}

	public List<UserExercise> getStudentsByExercise(Exercise exercise) {
		List<UserExercise> foundStudents = userExerciseRepository.findAllByExercise(exercise);
		return foundStudents;
	}

	public List<UserExercise> getExercisesByStudentId(String id) {
		return userExerciseRepository.findByUserStudent(userService.getStudentById(id)
				.orElseThrow(() -> new UserNotFoundException("Student not found: " + id)));
	}


	public UserExercise setUserExerciseStatusData(UserExercise userExercise) { 
		UserExercise one = userExerciseRepository.findById(userExercise.getId())
				.orElseThrow(() -> new UserNotFoundException("UserExercise not found"));
		if(userExercise.getDate_status()==null) {
			Date date = new Date();
			one.setDate_status(date);
		}
		else {
			one.setDate_status(userExercise.getDate_status());
		}
		one.setStatus(userExercise.getStatus());
		return userExerciseRepository.save(one);
	}

	public String getLastDeliveredExerciseDate(Student student) {
		List<UserExercise> studentExercises = userExerciseRepository.findByUserStudent(student);
		List<String> lastDeliveredExerciseDate = new ArrayList<String>();
		
		if(studentExercises.size()!=0) {
			for(UserExercise exercise : studentExercises) {
				if(exercise.getUserStudent()!=null && exercise.getStatus().getId()!=3) {
					lastDeliveredExerciseDate.add(exercise.getDate_status().toString());
				}
				else {
					lastDeliveredExerciseDate.add(exercise.getDate_status().toString() + " Pendent de revisió.");
				}
			}
			Collections.sort(lastDeliveredExerciseDate, Collections.reverseOrder());
			return lastDeliveredExerciseDate.get(0);
		}
		else {
			return "L'Usuari no té exercisis.";
		}
	}

	public List<DTOStudentsExerciseNotTurnedInI> getNotifyNotTurnedInExercises() {
		Integer notTurnedInDays = ApplicationConfig.EXERCISE_NOT_TURNED_IN_DAYS;
		List<Integer> exerciseStatusExclude = ApplicationConfig.EXERCISE_STATUS_EXCLUDE;
		return userExerciseRepository.getNotifyNotTurnedInExercises(notTurnedInDays, exerciseStatusExclude);
	}

}