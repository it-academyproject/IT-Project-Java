package com.it_academyproject.jwt_security.model;

import com.it_academyproject.domains.MyAppUser;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken
{
	private static final int EXPIRATION = 60 * 24;

	static final long ONE_MINUTE_IN_MILLIS = 60000;// milliseconds

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = MyAppUser.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id")
	private MyAppUser myAppUser;

	private Date expiryDate;

	// -------------------- -------------------- //
	
	public PasswordResetToken()
	{

	}

	public PasswordResetToken(String token, MyAppUser myAppUser)
	{
		this.token = token;
		this.myAppUser = myAppUser;
		Calendar date = Calendar.getInstance();
		long t = date.getTimeInMillis();
		Date afterAddingTenMins = new Date(t + (EXPIRATION * ONE_MINUTE_IN_MILLIS));

		this.setExpiryDate(afterAddingTenMins);
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

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public MyAppUser getMyAppUser()
	{
		return myAppUser;
	}

	public void setMyAppUser(MyAppUser myAppUser)
	{
		this.myAppUser = myAppUser;
	}

	public Date getExpiryDate()
	{
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate)
	{
		this.expiryDate = expiryDate;
	}

}