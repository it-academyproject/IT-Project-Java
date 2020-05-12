package com.it_academyproject.domains;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

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

	private Boolean active;

	// -------------------- -------------------- //

	public Project(String name, String description, Date initialDate, boolean active)
	{
		super();
		this.name = name;
		this.description = description;
		this.initialDate = initialDate;
		this.active = active;
	}

	public Project()
	{
		super();
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

	public Boolean isActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

}
