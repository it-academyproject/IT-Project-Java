package com.it_academyproject.domains;

public enum DataImportType {

	STUDENT(1), EXERCISE(2);

	private final int id;

	private DataImportType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;

	}

}
