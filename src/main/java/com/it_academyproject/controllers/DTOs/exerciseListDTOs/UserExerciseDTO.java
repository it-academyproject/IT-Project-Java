package com.it_academyproject.controllers.DTOs.exerciseListDTOs;

import java.util.Date;


import com.it_academyproject.domains.UserExercise;

public class UserExerciseDTO {

	
	String id;
	String studentName;
	String studentLastName;
	String statusExercise;
	Date date;
	
	
	public UserExerciseDTO(UserExercise userExercise) {
	
		this.id=userExercise.getUserStudent().getId();
		this.studentName=userExercise.getUserStudent().getFirstName();
		this.studentLastName=userExercise.getUserStudent().getLastName();
		this.statusExercise=userExercise.getStatusExercise().getName();
		this.date=userExercise.getDate_status();
		
		
	}
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public String getStudentLastName() {
		return studentLastName;
	}


	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}


	public String getStatusExercise() {
		return statusExercise;
	}


	public void setStatusExercise(String statusExercise) {
		this.statusExercise = statusExercise;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
	
}
