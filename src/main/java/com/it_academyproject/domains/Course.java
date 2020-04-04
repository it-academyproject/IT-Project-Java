package com.it_academyproject.domains;


import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Course {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@JsonView(View.Summary.class)
	private int id;
	private Date beginDate;
	@JsonView(View.Summary.class)
	private Date endDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="student_id")
	private Student userStudent;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	private Teacher teacher;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="itinerary_id")
	@JsonView(View.Summary.class)
	private Itinerary itinerary;
	
	public Course() {
	}

	public Course(int id, Date beginDate, Date endDate) {
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", beginDate=" + beginDate + ", endDate=" + endDate + "]";
	}

	public Student getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(Student userStudent) { this.userStudent = userStudent; }

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}
}
