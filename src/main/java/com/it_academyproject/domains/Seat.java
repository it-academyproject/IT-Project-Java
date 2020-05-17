package com.it_academyproject.domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

import javax.persistence.*;

@Entity
public class Seat
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonView(View.Summary.class)
	private int rowNumber;
	
	@JsonView(View.Summary.class)
	private int colNumber;
	
	@JsonView(View.Summary.class)
	private int classRoom;

	//@OneToMany private List<Student> students;
	
	// -------------------- -------------------- //

	public Seat()
	{

	}

	public int getId()
	{
		return id;
	}

	// -------------------- -------------------- //
	
	public void setId(int id)
	{
		this.id = id;
	}

	public int getRowNumber()
	{
		return rowNumber;
	}

	public void setRowNumber(int rowNumber)
	{
		this.rowNumber = rowNumber;
	}

	public int getColNumber()
	{
		return colNumber;
	}

	public void setColNumber(int colNumber)
	{
		this.colNumber = colNumber;
	}

	public int getClassRoom()
	{
		return classRoom;
	}

	public void setClassRoom(int classRoom)
	{
		this.classRoom = classRoom;
	}

	/*
	public List<Student> getStudents() 
	{ 
		return students; 
	}
	 
	public void setStudents(List<Student> students) 
	{ 
		this.students = students; 
	}
	*/

}