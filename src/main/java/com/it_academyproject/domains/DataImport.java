package com.it_academyproject.domains;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DataImport {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Date importDate;
	
	@Enumerated(EnumType.STRING)
	private DataImportType importType;
	
	
	public DataImport() {
		
	}

	public DataImport(Date importDate, DataImportType importType) {
		this.importDate = importDate;
		this.importType = importType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public DataImportType getImportType() {
		return importType;
	}

	public void setImportType(DataImportType importType) {
		this.importType = importType;
	}
	
	

}
