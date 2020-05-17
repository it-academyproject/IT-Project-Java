package com.it_academyproject.exceptions;

public class EmptyFieldException extends Exception
{
	private static final long serialVersionUID = 1L;
	private String fieldName;
	private String message;

	// -------------------- -------------------- //
	
	public EmptyFieldException(String fieldName)
	{
		super("The field " + fieldName + " is cannot be empty");
		this.fieldName = fieldName;
	}

	public EmptyFieldException(String fieldName, String type)
	{
		super("The field " + fieldName + " is " + type);
		this.fieldName = fieldName;
	}

	// -------------------- -------------------- //
	
	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}