package com.it_academyproject.controllers.DTOs.exerciseListDTOs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.UserExercise;

public class ExerciseListDTO {
	
	@Autowired
	UserExercise userExercise;
	
	int id;
	String name;
	int itinerary;
	List<UserExerciseDTO> students;
	

	public ExerciseListDTO(int id,String name, Itinerary itinerary, List<UserExercise> students) {
	
		this.id=id;
		this.name=name;
		this.itinerary=itinerary.getId();
		this.students=selectRelevantData(students);
	}


	private List<UserExerciseDTO> selectRelevantData(List<UserExercise> students) {
		
		List<UserExerciseDTO> userExerciseDTO = new ArrayList<UserExerciseDTO>();	
		
		for (UserExercise userExercise: students)	
		{	
			if (userExercise.getUserStudent()!=null)
			{
				userExerciseDTO.add(new UserExerciseDTO(userExercise));
			}
		}
		return userExerciseDTO;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public List<UserExerciseDTO> getStudents() {
		return students;
	}


	public void setStudents(List<UserExerciseDTO> students) {
		this.students = students;
	}
	
	

}
