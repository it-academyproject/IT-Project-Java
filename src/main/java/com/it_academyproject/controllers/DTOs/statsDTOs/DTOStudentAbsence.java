package com.it_academyproject.controllers.DTOs.statsDTOs;

public class DTOStudentAbsence {

	private final String id;
	private final String firstName;
	private final String lastName;
	private final int absences;

	public DTOStudentAbsence(String id, String firstName, String lastName, int absences) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.absences = absences;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAbsences() {
		return absences;
	}

}