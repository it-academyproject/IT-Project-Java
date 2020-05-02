package com.it_academyproject.services;

import com.it_academyproject.domains.*;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.repositories.UserExerciseRepository;
import com.it_academyproject.repositories.ExerciseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserExerciseService
{
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

	public JSONObject getExerciseStudentByItinerary (String itineraryIdString )
	{
		try
		{
			int itineraryId = Integer.parseInt(itineraryIdString);
			Itinerary itinerary = itineraryRepository.findOneById( itineraryId );
			List<Course> courseList = courseRepository.findByItineraryAndEndDate(itinerary , null);
			List<Student> students = new ArrayList<>();
			Map<String , List<UserExercise>> userUserExerciseMap = new HashMap<>();


			for (int i = 0; i < courseList.size() ; i++)
			{
				Student student = courseList.get( i ).getUserStudent();
				students.add( student);
				List<UserExercise> userExerciseList = userExerciseRepository.findByUserStudent(student);
				//remove the Student field that is not necessary
				for (int j = 0; j < userExerciseList.size(); j++)
				{
					userExerciseList.get(j).setUserStudent ( null );
				}
				userUserExerciseMap.put(student.getId() , userExerciseList);
			}

			JSONObject sendData = new JSONObject( userUserExerciseMap );
			return sendData;
		}
		catch (Exception e)
		{
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type" , "error");
			message.put("message" , exceptionMessage);
			sendData.put("Message" , message);
			return sendData;
		}
	}

	public List<Exercise> getAllExercises(){
		List<Exercise> allExercises = exerciseRepository.findAll();
		return allExercises;
	}



	public JSONObject getExerciseStudentByStudent(Student student) {
		try
		{

			//get only active students
			List<Course> courseList = courseRepository.findByUserStudent(student);
			List<Student> studentList = new ArrayList<>();
			Map<String , List<UserExercise>> userUserExerciseMap = new HashMap<>();
			for (int i = 0; i < courseList.size() ; i++)
			{
				Student myAppUser = courseList.get( i ).getUserStudent();
				studentList.add( myAppUser);
				List<UserExercise> userExerciseList = userExerciseRepository.findByUserStudent(myAppUser);
				//remove the Student field that is not necessary
				for (int j = 0; j < userExerciseList.size(); j++)
				{
					userExerciseList.get(j).setUserStudent ( null );
				}
				userUserExerciseMap.put(myAppUser.getId() , userExerciseList);
			}
			JSONObject sendData = new JSONObject( userUserExerciseMap );
			return sendData;
		}
		catch (Exception e)
		{
			String exceptionMessage = e.getMessage();
			JSONObject sendData = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type" , "error");
			message.put("message" , exceptionMessage);
			sendData.put("Message" , message);
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

	public boolean setUserExerciseStatusData(UserExercise userExercise) {
		System.out.println("UserExercise: " + userExercise.toString());
		List <UserExercise> list = userExerciseRepository.findAll();
		for (int i=0; i<list.size(); i++) {

			if (list.get(i).getId() == userExercise.getId()) {
				Date date = new Date();	
				list.get(i).setDate_status(date);
				list.get(i).setStatusExercise(userExercise.getStatusExercise());
				userExerciseRepository.save(list.get(i));
				return true;
			}
		}
		return false;
	}
}


