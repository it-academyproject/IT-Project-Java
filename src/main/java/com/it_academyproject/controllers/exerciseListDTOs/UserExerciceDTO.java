package com.it_academyproject.controllers.exerciseListDTOs;

import java.util.Date;


import com.it_academyproject.domains.UserExercice;

public class UserExerciceDTO {

	

	String studentName;
	String studentLastName;
	String statusExercise;
	Date date;
	
	
	public UserExerciceDTO(UserExercice userExercice) {
	
		this.studentName=userExercice.getUserStudent().getFirstName();
		this.studentLastName=userExercice.getUserStudent().getLastName();
		this.statusExercise=userExercice.getStatusExercice().getName();
		this.date=userExercice.getDate_status();
		
		
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
