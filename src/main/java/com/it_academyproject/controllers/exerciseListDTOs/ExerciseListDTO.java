package com.it_academyproject.controllers.exerciseListDTOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.UserExercice;

public class ExerciseListDTO {
	
	
	String name;
	int itinerary;
	Map<String,UserExerciceDTO> students;
	

	public ExerciseListDTO(String name, Itinerary itinerary, List<UserExercice> students) {
	
		this.name=name;
		this.itinerary=itinerary.getId();
		this.students=selectRelevantData(students);
	}


	private Map<String, UserExerciceDTO> selectRelevantData(List<UserExercice> students) {
		
		Map<String,UserExerciceDTO> userExerciceDTO = new HashMap<String,UserExerciceDTO>();	
		
		for (UserExercice userExercice: students)	
		{	
			if (userExercice.getUserStudent()!=null)
			{
			userExerciceDTO.put(userExercice.getUserStudent().getId(),new UserExerciceDTO(userExercice));
			}
		}
		return userExerciceDTO;
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


	public Map<String,UserExerciceDTO> getStudents() {
		return students;
	}


	public void setStudents(Map<String,UserExerciceDTO> students) {
		this.students = students;
	}
	
	

}
