package com.it_academyproject.jwt_security.security;

public class LoginData
{
	private String email;

	private String password;
	
	// -------------------- -------------------- //
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{

		this.password = password;
	}

}