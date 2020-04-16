package com.it_academyproject.domains;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

@Entity
public class Itinerary {
	
	//--------------------------Properties--------------------------------------------------------------

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(View.Summary.class)
	private int id;
	@JsonView(View.Summary.class)
	private String name;
	
	//--------------------------Constructors--------------------------------------------------------------
	
	public Itinerary() {
		
	}
	
	public Itinerary(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy="itinerary")
	@JsonIgnore
	Set<ProjectItinerary> projectItineraries;
	
	
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