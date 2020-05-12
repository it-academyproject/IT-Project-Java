package com.it_academyproject.domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.controllers.DTOs.exerciseListDTOs.ExerciseListDTO;
import com.it_academyproject.exceptions.EmptyFieldException;
import com.it_academyproject.repositories.UserExerciseRepository;
import com.it_academyproject.tools.View;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("student")
public class Student extends MyAppUser 
{	
	@Autowired
	UserExerciseRepository userExerciseRepository;
	
	@JsonView(View.Summary.class)
	@JoinColumn
	private String lastExerciseDelivered;

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
	
	/////////////////////
	
	public String getLastExerciseDelivered(Student user, Exercise exercises) 
	{
		List<UserExercise> lastExerciseDeliveredList = userExerciseRepository.findAllByRecentExerciseDelivered(exercises);
		
		for (UserExercise exercise : lastExerciseDeliveredList)
		{
			if (exercise.getUserStudent().equals(user.getId()))
			{
				lastExerciseDelivered = exercise.getDate_status().toString();
			}
		}
		return lastExerciseDelivered;
	}

	public void setLastExerciseDelivered(String lastExerciseDelivered) 
	{
		this.lastExerciseDelivered = lastExerciseDelivered;
	}
	
	/////////////////////

}