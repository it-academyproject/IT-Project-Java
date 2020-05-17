package com.it_academyproject.exceptions;

public class WrongEmailPassword extends Exception
{
	private static final long serialVersionUID = 1L;

	private String message;

	// -------------------- -------------------- //
	
	public WrongEmailPassword()
	{
		super("Wrong Email or Password.");
		this.message = "Wrong Email or Password.";
	}

	// -------------------- -------------------- //
	
	@Override
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}