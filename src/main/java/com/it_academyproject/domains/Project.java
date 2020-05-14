package com.it_academyproject.domains;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "project")
public class Project
{
	private @Id @GeneratedValue Long id;

	private Long idParent;

	private String name;

	private String description;

	private Date initialDate;

	@OneToMany(mappedBy = "project")
	@JsonIgnore
	Set<ProjectItinerary> projectItineraries;

	@ManyToMany//(mappedBy = "project")
	private List<Iteration> iterations;

	private Boolean active;

	// -------------------- -------------------- //

	public Project(String name, String description, Date initialDate, List<Iteration> iterations, boolean active)
	{
		super(); // Do we really need this?
		this.name = name;
		this.description = description;
		this.initialDate = initialDate;
		this.active = active;
		this.iterations = iterations;
	}

	public Project()
	{
		super(); // Do we really need it? this is a default field when you generate an empty
					// constructor
	}

	// -------------------- -------------------- //

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getInitialDate()
	{
		return initialDate;
	}

	public void setInitialDate(Date initialDate)
	{
		this.initialDate = initialDate;
	}

	public Set<ProjectItinerary> getProjectItineraries()
	{
		return projectItineraries;
	}

	public void setProjectItineraries(Set<ProjectItinerary> projectItineraries)
	{
		this.projectItineraries = projectItineraries;
	}

	public Long getIdParent()
	{
		return idParent;
	}

	public void setIdParent(Long idParent)
	{
		this.idParent = idParent;
	}

	public List<Iteration> getIterations()
	{
		return iterations;
	}

	public void setIterations(List<Iteration> iterations)
	{
		this.iterations = iterations;
	}

	public Boolean isActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}
	
}
