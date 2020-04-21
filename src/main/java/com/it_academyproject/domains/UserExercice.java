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
public class UserExercice {
	
	//--------------------------Properties--------------------------------------------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Id;
	private String comments;
	private Date date_status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="statusExercice_id")
	private StatusExercice statusExercice;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="exercice_id")
	private Exercice exercice;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="student_id")
	@JsonView({View.SummaryWithOthers.class , View.noShow.class})
	private MyAppUser userStudent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	private MyAppUser userTeacher;

	//--------------------------Constructors--------------------------------------------------------------
	

	public UserExercice() {
	}
	
	public UserExercice(String comments, Date date_status) {
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

	public StatusExercice getStatusExercice() {
		return statusExercice;
	}

	public void setStatusExercice(StatusExercice statusExercice) {
		this.statusExercice = statusExercice;
	}

	public Exercice getExercice() {
		return exercice;
	}

	public void setExercice(Exercice exercice) {
		this.exercice = exercice;
	}

	public MyAppUser getUserStudent() {
		return userStudent;
	}

	public void setUserStudent(MyAppUser userStudent) {
		this.userStudent = userStudent;
	}

	public MyAppUser getUserTeacher() {
		return userTeacher;
	}

	public void setUserTeacher(MyAppUser userTeacher) {
		this.userTeacher = userTeacher;
	}


	

}