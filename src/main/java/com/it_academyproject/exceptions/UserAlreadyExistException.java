package com.it_academyproject.exceptions;

public final class UserAlreadyExistException extends RuntimeException
{
	private static final long serialVersionUID = 5861310537366287163L;

	// -------------------- -------------------- //
	
	public UserAlreadyExistException()
	{
	
	}

	public UserAlreadyExistException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public UserAlreadyExistException(final String message)
	{
		super(message);
	}

	public UserAlreadyExistException(final Throwable cause)
	{
		super(cause);
	}

}