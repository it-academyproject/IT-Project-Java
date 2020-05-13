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
public class UserExercise {
	
	//--------------------------Properties--------------------------------------------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Id;
	private String comments;
	private Date date_status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="status_id")
	private StatusExercise statusExercise;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="exercise_id")
	private Exercise exercise;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="student_id")
	@JsonView({View.SummaryWithOthers.class , View.noShow.class})
	private Student userStudent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	private Teacher userTeacher;

	//--------------------------Constructors--------------------------------------------------------------
	

	public UserExercise() {
	}
	
	public UserExercise(String comments, Date date_status) {
		this.comments = comments;
		this.date_status = new Date();
	}
	
	//--------------------------Setters/Getters--------------------------------------------------------------------
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getDate_status() {
		return date_status;
	}
	public void setDate_status(Date date_status) {
		this.date_status = date_status;
	}

	public StatusExercise getStatusExercise() {
		return statusExercise;
	}

	public void setStatusExercise(StatusExercise statusExercise) {
		this.statusExercise = statusExercise;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public Student getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(Student  userStudent) {
		this.userStudent = userStudent;
	}

	public Teacher getUserTeacher() {
		return userTeacher;
	}

	public void setUserTeacher(Teacher userTeacher) {
		this.userTeacher = userTeacher;
	}

	@Override
	public String toString() {
		return "UserExercise{" +
				"Id=" + Id +
				", comments='" + comments + '\'' +
				", date_status=" + date_status +
				", statusExercise=" + statusExercise +
				", exercise=" + exercise +
				", userStudent=" + userStudent +
				", userTeacher=" + userTeacher +
				'}';
	}
}