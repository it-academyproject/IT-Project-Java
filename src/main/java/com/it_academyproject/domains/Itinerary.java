package com.it_academyproject.domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Itinerary {
	
	//--------------------------Properties--------------------------------------------------------------

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(View.Summary.class)
	private int id;
	private String name;
	
	//--------------------------Constructors--------------------------------------------------------------
	
	public Itinerary() {
	}
	
	public Itinerary(String name) {
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