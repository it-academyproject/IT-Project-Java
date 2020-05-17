package com.it_academyproject.domains;

import com.it_academyproject.exceptions.EmptyFieldException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("teacher")
public class Teacher extends MyAppUser
{
	public Teacher()
	{
		this.setRole(Role.TEACHER);
	}

	public Teacher(String firstName, String lastName, String email, char gender, String portrait, String password, boolean enabled)
	{
		super(firstName, lastName, email, gender, portrait, password, enabled, Role.TEACHER);
	}

	public Teacher(String email, String password) throws EmptyFieldException
	{
		super(email, password);
		this.setRole(Role.TEACHER);
	}

}