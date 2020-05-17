package com.it_academyproject.exceptions;

public final class InvalidPasswordException extends RuntimeException
{
	private static final long serialVersionUID = 5861310537366287163L;

	private String message;

	// -------------------- -------------------- //
	
	public InvalidPasswordException()
	{
	
	}

	public InvalidPasswordException(String errorType)
	{
		if (errorType.equals("format"))
		{
			this.message = "The Password has an invalid format";
		}
	}

	// -------------------- -------------------- //
	
	public static long getSerialVersionUID()
	{
		return serialVersionUID;
	}

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