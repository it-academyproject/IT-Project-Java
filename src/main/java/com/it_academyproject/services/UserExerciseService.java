package com.it_academyproject.services;

import com.it_academyproject.domains.*;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.ExerciceRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.UserExerciceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserExerciseService
{
	@Autowired
	UserExerciceRepository userExerciceRepository;
	@Autowired
	MyAppUserService userService;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ItineraryRepository itineraryRepository;
	@Autowired
	ExerciceRepository exerciseRepository;

	public JSONObject getExerciseStudentByItinerary (String itineraryIdString )
	{
		try
		{
			int itineraryId = Integer.parseInt(itineraryIdString);
			Itinerary itinerary = itineraryRepository.findOneById( itineraryId );
			List<Course> courseList = courseRepository.findByItineraryAndEndDate(itinerary , null);
			List<MyAppUser> myAppUserList = new ArrayList<>();
			Map<String , List<UserExercice>> userUserExerciceMap = new HashMap<>();

			for (int i = 0; i < courseList.size() ; i++)
			{
				MyAppUser myAppUser = courseList.get( i ).getUserStudent();
				myAppUserList.add( myAppUser);
				List<UserExercice> userExerciceList = userExerciceRepository.findByUserStudent(myAppUser);
				//remove the Student field that is not necessary
				for (int j = 0; j < userExerciceList.size(); j++)
				{
					userExerciceList.get(j).setUserStudent ( null );
				}
				userUserExerciceMap.put(myAppUser.getId() , userExerciceList );
			}

			JSONObject sendData = new JSONObject( userUserExerciceMap );
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

	public List<Exercice> getAllExercises(){
		List<Exercice> allExercises = exerciseRepository.findAll();
		return allExercises;
	}

	public List<UserExercice> getStudentsByExercise(Exercice exercise) {
		List<UserExercice> foundStudents = userExerciceRepository.findAllByExercice(exercise);
		return foundStudents;
	}

	public List<UserExercice> getExercisesByStudentId(String id) {
		return userExerciceRepository.findByUserStudent(userService.getStudentById(id)
				.orElseThrow(() -> new UserNotFoundException("Student not found: " + id)));
	}

	public boolean setUserExerciseStatusData(UserExercice userExercice) {
		List <UserExercice> list = userExerciceRepository.findAll();
		for (int i=0; i<list.size(); i++) {
			if (list.get(i).getId() == userExercice.getId()) {
				Date date = new Date();
				list.get(i).setDate_status(date);
				list.get(i).setStatusExercice(userExercice.getStatusExercice());
				userExerciceRepository.save(list.get(i));
				return true;
			}
		}
		return false;
	}
}


