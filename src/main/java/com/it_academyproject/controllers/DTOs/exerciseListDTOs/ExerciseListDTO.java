package com.it_academyproject.controllers.DTOs.exerciseListDTOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.UserExercise;

public class ExerciseListDTO {
	
	
	String name;
	int itinerary;
	Map<String,UserExerciseDTO> students;
	

	public ExerciseListDTO(String name, Itinerary itinerary, List<UserExercise> students) {
	
		this.name=name;
		this.itinerary=itinerary.getId();
		this.students=selectRelevantData(students);
	}


	private Map<String, UserExerciseDTO> selectRelevantData(List<UserExercise> students) {
		
		Map<String,UserExerciseDTO> userExerciseDTO = new HashMap<String,UserExerciseDTO>();	
		
		for (UserExercise userExercise: students)	
		{	
			if (userExercise.getUserStudent()!=null)
			{
			userExerciseDTO.put(userExercise.getUserStudent().getId(),new UserExerciseDTO(userExercise));
			}
		}
		return userExerciseDTO;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getItinerary() {
		return itinerary;
	}


	public void setItinerary(int itinerary) {
		this.itinerary = itinerary;
	}


	public Map<String,UserExerciseDTO> getStudents() {
		return students;
	}


	public void setStudents(Map<String,UserExerciseDTO> students) {
		this.students = students;
	}
	
	

}
