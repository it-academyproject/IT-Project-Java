package com.it_academyproject.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

import java.util.Date;

@Entity
public class Absence {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	@JsonView(View.Summary.class)
	private int id;
	
	@JsonView(View.Summary.class)
	private Date dateMissing; 
	
	@JsonView(View.Summary.class)
	private String comment; 
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JsonView(View.Summary.class)
	@JoinColumn (name="student_id")
	@JsonProperty(access = Access.READ_ONLY)
	private MyAppUser userStudent;
	
	public Absence() {
		
	}
	public Absence(Date dateMissing, String comment) {
		
		this.comment = comment;
		this.dateMissing = dateMissing;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateMissing() {
		return dateMissing;
	}

	public void setDateMissing(Date dateMissing) {
		this.dateMissing = dateMissing;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public MyAppUser getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(MyAppUser userStudent) {
		this.userStudent = userStudent;
	}

	@Override
	public String toString() {
		return "Absence [id=" + id + ", dateMissing=" + dateMissing + ", user=" + userStudent + "]";
	}

	
}


