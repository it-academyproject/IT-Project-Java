package com.it_academyproject.exceptions;

public final class BadRoleException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public BadRoleException()
	{
		super("Invalid user role.");
	}

	public BadRoleException(String message)
	{
		super(message);
	}

}