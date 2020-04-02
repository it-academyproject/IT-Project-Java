package com.it_academyproject.domains;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StatusExercise {
	
	//--------------------------Properties--------------------------------------------------------------
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	
	//--------------------------Constructors--------------------------------------------------------------

	public StatusExercise() {
	}
	
	public StatusExercise(String name) {
		this.name = name;
	}
	
	//--------------------------Setters/Getters--------------------------------------------------------------------

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

}