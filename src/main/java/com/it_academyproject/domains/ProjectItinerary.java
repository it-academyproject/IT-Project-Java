package com.it_academyproject.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="project_itinerary")
public class ProjectItinerary {
	@Id 
	@GeneratedValue 
	Long id;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="project_id")
	private Project project;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="itinerary_id")
	private Itinerary itinerary;
	
	
	
	
	public ProjectItinerary(ProjectItinerary PI) {
		super();
		this.project = PI.getProject();
		this.itinerary = PI.getItinerary();
	}

	
	public ProjectItinerary(Project project, Itinerary itinerary) {
		super();
		this.id = id;
		this.project = project;
		this.itinerary = itinerary;
	}
	
	public ProjectItinerary() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	
	
		
}
