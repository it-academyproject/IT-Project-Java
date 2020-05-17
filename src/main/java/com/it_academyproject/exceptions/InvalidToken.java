package com.it_academyproject.exceptions;

public class InvalidToken extends Exception
{
	private static final long serialVersionUID = 1L;

	private String message;

	// -------------------- -------------------- //
	
	public InvalidToken(String errorType)
	{
		if (errorType.equals("expired"))
		{
			this.message = "The token has expired.";
		}
		if (errorType.equals("not found"))
		{
			this.message = "The token is not valid.";
		}
		if (errorType.equals("No user"))
		{
			this.message = "The token doesn't have a valid user associated.";
		}
		if (errorType.equals("Email"))
		{
			this.message = "The email doesn't match with the user email.";
		}
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