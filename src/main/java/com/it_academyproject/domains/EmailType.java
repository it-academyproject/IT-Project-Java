package com.it_academyproject.domains;

public enum EmailType
{
	ABSENCES(1), DAYSLEFT15(2), DAYSLEFT30(3);

	private final int id;

	// -------------------- -------------------- //
	
	EmailType(int id)
	{
		this.id = id;
	}

	int id()
	{
		return id;
	}

	// -------------------- -------------------- //
	
	public int getId()
	{
		return id;
	}

}