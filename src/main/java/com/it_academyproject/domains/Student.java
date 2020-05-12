package com.it_academyproject.domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseListDTO;
import com.it_academyproject.exceptions.EmptyFieldException;
import com.it_academyproject.tools.View;

import javax.persistence.*;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("student")
public class Student extends MyAppUser 
{	
	@JsonView(View.Summary.class)
	@JoinColumn
	private Date lastExerciseDelivered;

	@JsonView(View.Summary.class)
	@ManyToOne
	private Seat seat;
	
	// -------------------- -------------------- //

	public Student() 
	{
		super();
		this.setRole(Role.STUDENT);
	}

	public Student(String firstName, String lastName, String email, char gender, String portrait, String password, boolean enabled) 
	{
		super(firstName, lastName, email, gender, portrait, password, enabled, Role.STUDENT);
	}

	public Student(String email, String password) throws EmptyFieldException 
	{
		super(email, password);
		this.setRole(Role.STUDENT);
	}

	// -------------------- -------------------- //

	public Seat getSeat() 
	{
		return seat;
	}

	public void setSeat(Seat seat) 
	{
		this.seat = seat;
	}
	
	@GetMapping("/test")
	public Date getLastExerciseDelivered(List<UserExercise> exerciseList) 
	{
		for (UserExercise exercise : exerciseList)
		{
			exercise.getDate_status();
		}
		return lastExerciseDelivered;		
	}

	public void setLastExerciseDelivered(Date lastExerciseDelivered) 
	{
		this.lastExerciseDelivered = lastExerciseDelivered;
	}
	
}