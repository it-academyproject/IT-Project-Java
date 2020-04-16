package com.it_academyproject.services;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.Exercice;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercice;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.ExerciceRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.repositories.UserExerciceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserExerciseService {
	@Autowired
	UserExerciceRepository userExerciceRepository;
	@Autowired
	ExerciceRepository exerciseRepository;
	@Autowired
	MyAppUserRepository myAppUserRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ItineraryRepository itineraryRepository;

	// -----------------------------B-36---GET ALL EXERCISES PRUEBA PARTIENDO DEL BLOQUE DE ABAJO QUE ESTÁ COMENTADO-------------------------------------------------------------------------------------

	// List exercises LIKE THAT:

	// Exercise id {
	
		// exercise name
		// itinerary id
		// student: (in every student...)
			//{
			// student name
			// exercise status
			// date status
			// }
	//}
	public JSONObject getAllExercises() {

		//get all exercises
		List<Exercice> exerciseList = exerciseRepository.findAll();
		//get all students
//		List<MyAppUser> studentsList = myAppUserRepository.findAll();
		//JSON that will return all exercises with their whole info stored
		JSONObject exerciseJSON = new JSONObject();
		//JSON that will return the values of each exercise
		JSONObject exerciseValuesJSON = new JSONObject();
		//JSON that will return the info of exercise 'x' of 'y' student
		JSONObject userJSON = new JSONObject();
		//JSON that will return the values of each user
		JSONObject userValuesJSON = new JSONObject();

		//search in each exercise
		for (int i = 0; i < exerciseList.size(); i++) {
			
			//get values of exercise (i)
			Exercice exercise = exerciseList.get(i);
			String exerciseId = Integer.toString(exercise.getId());
			String exerciseName = exercise.getName();
			String exerciseItinerary = Integer.toString(exercise.getItinerary().getId());
			
			//search each student with that exercise(i) ----------------- NOT WORKING THROWS ERROR MESSAGE IN POSTMAN
//			for (int j = 0; j < studentsList.size(); j++) {
//				
//				//get values of student(i)
//				MyAppUser student = studentsList.get(j);
//				String studentId = student.getId();
//				
//				//get all userexercises
////				List<UserExercice> userExercisesList = userExerciceRepository.findByUserStudent(student);
//				UserExercice userExercise = userExerciceRepository.getOne(exercise.getId()); //.findOneByUserStudent(studentId);
//				String studentName = userExercise.getUserStudent().getfullName();
//				String exerciseStatus = userExercise.getStatusExercice().toString();
//				String dateStatus = userExercise.getDate_status().toString();
//								
//				userValuesJSON.put("ExerciseStatus", exerciseStatus);
//				userValuesJSON.put("DateStatus", dateStatus);
//				
//				userJSON.put("StudentName_" + studentName, userValuesJSON);	
//			}
		
			exerciseValuesJSON.put("Name", exerciseName);
			exerciseValuesJSON.put("Itinerary", exerciseItinerary);
//			exerciseValuesJSON.put("User_", userValuesJSON);
			
			exerciseJSON.put("Exercise_" + exerciseId, exerciseValuesJSON); 

			//EN CONSOLE ME DEVUELVE EL RESULTADO DE TODOS LOS EJERCICIOS ORDENADOS, PERO JSON LOS TIRA DESORDENADOS...
			
			System.out.println(exerciseId + ", " + exerciseName  + ", " +  exerciseItinerary);
			
		}
		
		return exerciseJSON;
		
	}
	
	
//---------------BLOQUE ANTERIOR QUE DEVUELVE JSON RARO ORIGINAL--------------------------------------------------------
//	
//	public JSONObject getExercisesById (String exerciseId ){
//		
//		try
//		{
//						
//			int exerciseIntId = Integer.parseInt(exerciseId);
//			List<Exercice> exerciseList = exerciseRepository.findAll();
//			List<Exercice> exerciseListTwo = new ArrayList<>();
//			
//			for (int i = 0; i < exerciseList.size(); i++) {
//				
//				Exercice exercise = exerciseList.get(i);
//				exercise.getName();
//				exercise.getItinerary();
//				exerciseListTwo.add(exercise);
//				
//			}
//					
//			JSONObject sendData = new JSONObject( exerciseList );
//			return sendData;
//			
//			
//			Exercice exercise = exerciseRepository.findOneById( exerciseIntId );			
//			UserExercice userExercise = userExerciceRepository.findByExercise(exercise);
			
//			List <Itinerary> itineraryList = itineraryRepository.findeOneByExercise(exercise);
//			Itinerary itinerary = itineraryRepository.findOneById(exerciseIntId);
//			List<Course> courseList = courseRepository.findByItineraryAndEndDate(itinerary , null);
//
////			List<UserExercice> usersExercisesList = userExerciceRepository.findOneByItineraryAndExercice(itinerary, exercise);
//			List<MyAppUser> myAppUserList = new ArrayList<>();
//			Map<String , List<UserExercice>> userExerciseMap = new HashMap<>();
//			
//			for (int i = 0; i < courseList.size() ; i++){
				
//				MyAppUser myAppUser = courseList.get( i ).getUserStudent();
//				myAppUserList.add( myAppUser);
//				List<UserExercice> userExercisesList = userExerciceRepository.findByUserStudent(myAppUser);
//				
//				
//				//remove the Student and Teacher fields that are not necessary
//				for (int j = 0; j < userExercisesList.size(); j++)
//				{
//					userExercisesList.get(j).setUserStudent ( null );
//					userExercisesList.get(j).setUserTeacher(null);
//				}
//				userExerciseMap.put(myAppUser.getfullName() , userExercisesList );
//			
//			
//			
//			}
//			
//			JSONObject sendData = new JSONObject( userExerciseMap );
//			return sendData;
//			
//		} catch(Exception e){
//			
//		String exceptionMessage = e.getMessage();
//		JSONObject sendData = new JSONObject();
//		JSONObject message = new JSONObject();
//		message.put("type", "error");
//		message.put("message", exceptionMessage);
//		sendData.put("Message", message);
//		return sendData;
//		
//		}
//		
//	}
// ---------------------------------------------------------------------------------------------------------------------------------------

	public JSONObject getExerciseStudentByItinerary(String itineraryIdString) {
		try {
			// convert the itineraryIdString to a int
			int itineraryId = Integer.parseInt(itineraryIdString);
			Itinerary itinerary = itineraryRepository.findOneById(itineraryId);
			// get only active students
			List<Course> courseList = courseRepository.findByItineraryAndEndDate(itinerary, null);
			List<MyAppUser> myAppUserList = new ArrayList<>();
			Map<String, List<UserExercice>> userUserExerciceMap = new HashMap<>();
			for (int i = 0; i < courseList.size(); i++) {
				MyAppUser myAppUser = courseList.get(i).getUserStudent();
				myAppUserList.add(myAppUser);
				List<UserExercice> userExerciceList = userExerciceRepository.findByUserStudent(myAppUser);
				// remove the Student and Teacher fields that are not necessary
				for (int j = 0; j < userExerciceList.size(); j++) {
					userExerciceList.get(j).setUserStudent(null);
					userExerciceList.get(j).setUserTeacher(null);
				}
				userUserExerciceMap.put(myAppUser.getfullName(), userExerciceList);
			}
			JSONObject sendData = new JSONObject(userUserExerciceMap);
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

	public JSONObject getExerciseStudentByItinerary() {
		// get all the itineraries
		List<Itinerary> itineraryList = itineraryRepository.findAll();
		JSONObject sendData = new JSONObject();
		for (int i = 0; i < itineraryList.size(); i++) {
			String itineraryId = Integer.toString(itineraryList.get(i).getId());
			JSONObject returnJson = getExerciseStudentByItinerary(itineraryId);
			sendData.put("Itinerary_" + itineraryId, returnJson);
		}
		return sendData;
	}

	public JSONObject getExerciseStudentByStudent(MyAppUser student) {
		try {

			// get only active students
			List<Course> courseList = courseRepository.findByUserStudent(student);
			List<MyAppUser> myAppUserList = new ArrayList<>();
			Map<String, List<UserExercice>> userUserExerciceMap = new HashMap<>();
			for (int i = 0; i < courseList.size(); i++) {
				MyAppUser myAppUser = courseList.get(i).getUserStudent();
				myAppUserList.add(myAppUser);
				List<UserExercice> userExerciceList = userExerciceRepository.findByUserStudent(myAppUser);
				// remove the Student and Teacher fields that are not necessary
				for (int j = 0; j < userExerciceList.size(); j++) {
					userExerciceList.get(j).setUserStudent(null);
					userExerciceList.get(j).setUserTeacher(null);
				}
				userUserExerciceMap.put(myAppUser.getId(), userExerciceList);
			}
			JSONObject sendData = new JSONObject(userUserExerciceMap);
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

	public boolean setUserExerciseStatusData(UserExercice userExercice) {
		List<UserExercice> list = userExerciceRepository.findAll();
		for (int i = 0; i < list.size(); i++) {
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
