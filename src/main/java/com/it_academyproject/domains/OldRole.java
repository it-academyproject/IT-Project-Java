package com.it_academyproject.domains;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// @Entity
public class OldRole {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	private int id;
	private String name;
	//@OneToMany (targetEntity = MyAppUser.class, cascade = CascadeType.ALL)
	//private List <MyAppUser> users;
	
	public OldRole() {
	}
	
	public OldRole(int id, String name) {
		
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", name=" + name + "]";
	}
	
}