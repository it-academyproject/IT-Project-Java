package com.it_academyproject.services;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercise;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.repositories.UserExerciseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserExerciseService
{
	@Autowired
	UserExerciseRepository userExerciseRepository;
	@Autowired
	MyAppUserRepository myAppUserRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ItineraryRepository itineraryRepository;

	public JSONObject getExerciseStudentByItinerary (String itineraryIdString )
	{
		try
		{
			//convert the itineraryIdString to a int
			int itineraryId = Integer.parseInt(itineraryIdString);
			Itinerary itinerary = itineraryRepository.findOneById( itineraryId );
			//get only active students
			List<Course> courseList = courseRepository.findByItineraryAndEndDate(itinerary , null);
			List<MyAppUser> myAppUserList = new ArrayList<>();
			Map<String , List<UserExercise>> userUserExerciceMap = new HashMap<>();
			for (int i = 0; i < courseList.size() ; i++)
			{
				MyAppUser myAppUser = courseList.get( i ).getUserStudent();
				myAppUserList.add( myAppUser);
				List<UserExercise> userExerciseList = userExerciseRepository.findByUserStudent(myAppUser);
				//remove the Student field that is not necessary
				for (int j = 0; j < userExerciseList.size(); j++)
				{
					userExerciseList.get(j).setUserStudent ( null );
				}
				userUserExerciceMap.put(myAppUser.getId() , userExerciseList);
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

	





	public JSONObject getExerciseStudentByItinerary (  )
	{
		//get all the itineraries
		List<Itinerary> itineraryList = itineraryRepository.findAll();
		JSONObject sendData = new JSONObject();
		for (int i = 0; i < itineraryList.size(); i++)
		{
			String itineraryId = Integer.toString( itineraryList.get(i).getId() );
			JSONObject returnJson = getExerciseStudentByItinerary ( itineraryId );
			sendData.put("Itinerary_" + itineraryId , returnJson );


		}
		return sendData;
	}




	public JSONObject getExerciseStudentByStudent(MyAppUser student) {
		try
		{

			//get only active students
			List<Course> courseList = courseRepository.findByUserStudent(student);
			List<MyAppUser> myAppUserList = new ArrayList<>();
			Map<String , List<UserExercise>> userUserExerciceMap = new HashMap<>();
			for (int i = 0; i < courseList.size() ; i++)
			{
				MyAppUser myAppUser = courseList.get( i ).getUserStudent();
				myAppUserList.add( myAppUser);
				List<UserExercise> userExerciseList = userExerciseRepository.findByUserStudent(myAppUser);
				//remove the Student field that is not necessary
				for (int j = 0; j < userExerciseList.size(); j++)
				{
					userExerciseList.get(j).setUserStudent ( null );
				}
				userUserExerciceMap.put(myAppUser.getId() , userExerciseList);
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



	public boolean setUserExerciseStatusData(UserExercise userExercise) {
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


