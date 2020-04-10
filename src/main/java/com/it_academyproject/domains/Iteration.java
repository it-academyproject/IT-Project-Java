package com.it_academyproject.domains;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

@Entity
@Table(name="iteration")
public class Iteration {
	
	@Id @GeneratedValue private Long id;
	
	private String iterationName;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="project_id")
	private Project project;
	
	private Date initialDate;
	
	private Date endDate;
	
	private String description;
	
//	@ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                CascadeType.PERSIST,
//                CascadeType.MERGE
//            },
//            mappedBy = "iterations")
//	@JsonIgnore
//    private Set<MyAppUser> users = new HashSet<>();
   
	@OneToMany(mappedBy="iteration")
	@JsonIgnore
	Set<UserIteration> userIterations;
	
	
	public Iteration() {
		super();
	}

	public Iteration(Long id, String iterationName, Project project, Date initialDate, Date endDate,
			String description) {
		super();
		this.id = id;
		this.iterationName = iterationName;
		this.project = project;
		this.initialDate = initialDate;
		this.endDate = endDate;
		this.description = description;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return iterationName;
	}

	public void setName(String name) {
		this.iterationName = name;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Set<MyAppUser> getUsers() {
//		return users;
//	}
//
//	public void setUsers(MyAppUser user) {
//		this.users.add(user);
//	}
	
	
}
