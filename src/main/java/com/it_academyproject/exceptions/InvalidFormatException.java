package com.it_academyproject.exceptions;

public final class InvalidFormatException extends RuntimeException
{
	private static final long serialVersionUID = 5861310537366287163L;

	String fieldName = "";

	// -------------------- -------------------- //
	
	public InvalidFormatException()
	{
	
	}

	public InvalidFormatException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public InvalidFormatException(final String fieldName)
	{
		super("The format of the " + fieldName + " is invalid.");
	}

	public InvalidFormatException(final Throwable cause)
	{
		super(cause);
	}

	// -------------------- -------------------- //
	
	public static long getSerialVersionUID()
	{
		return serialVersionUID;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

}