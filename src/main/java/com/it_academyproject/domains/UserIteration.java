package com.it_academyproject.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_iteration")
public class UserIteration
{
	@Id @GeneratedValue
	Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "my_app_user_id")
	private MyAppUser myAppUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "iteration_id")
	private Iteration iteration;

	// -------------------- -------------------- //
	
	public UserIteration()
	{
	
	}
	
	public UserIteration(MyAppUser myAppUser, Iteration iteration2)
	{
		this.myAppUser = myAppUser;
		this.iteration = iteration2;
	}

	public UserIteration(UserIteration UI)
	{
		this.myAppUser = UI.getMyAppUser();
		this.iteration = UI.getIteration();
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
	
	public MyAppUser getMyAppUser()
	{
		return myAppUser;
	}

	public void setMyAppUser(MyAppUser myAppUser)
	{
		this.myAppUser = myAppUser;
	}

	public Iteration getIteration()
	{
		return iteration;
	}

	public void setIteration(Iteration iteration)
	{
		this.iteration = iteration;
	}

}