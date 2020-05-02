package com.it_academyproject.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.it_academyproject.tools.View;

import java.util.Date;

@Entity
public class Absence {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@JsonView(View.Summary.class)
	private int id;
	@JsonInclude(Include.NON_NULL)
	@JsonView(View.Summary.class)
	private Date dateMissing;

//	@JsonInclude(value=Include.ALWAYS, content=Include.USE_DEFAULTS)
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(access = Access.READ_WRITE)
	@JsonView(View.Summary.class)
	private String comment;
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonView(View.Summary.class)
	@JoinColumn(name = "student_id", nullable = false)
//	@JsonProperty(access = Access.READ_ONLY)
	private Student userStudent;

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

	public Student getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(Student userStudent) {
		this.userStudent = userStudent;
	}

	@Override
	public String toString() {
		return "Absence [id=" + id + ", dateMissing=" + dateMissing + ", comment=" + comment + ", user=" + userStudent
				+ "]";
	}
}


