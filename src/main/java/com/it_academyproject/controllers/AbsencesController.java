package com.it_academyproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.services.AbsencesService;
import com.it_academyproject.tools.View;

@RestController
public class AbsencesController {

	@Autowired
	AbsenceRepository myAbsenceRepository;
	@Autowired
	AbsencesService absencesService;
	
	//Create absence by student id
    @PostMapping("/api/students/absences")//modificar URI
    public Absence createAbsence(@RequestBody Absence absence, MyAppUser userStudent) {
    	return absencesService.createAbsence(absence, userStudent.getId());
    }
    
    
	// Call total absences
	@JsonView(View.Summary.class)
	@GetMapping("/api/students/absences")
	public List<Absence> getAllAbsences() {
		return absencesService.getAllAbsences();
	}

	// Call absence by id
	@JsonView(View.Summary.class)
	@GetMapping("/api/students/absences/id")
	public Absence getAbsenceById(@RequestBody Absence absence) {
		return absencesService.getAbsenceById(absence);
	}

	
	
	//MAKE GET ABSENCES BY STUDENT ID!!
	
	
	
	// Edit Absence by id
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("api/students/absences")
	public Absence putAbsence(@RequestBody Absence absence) {
		return absencesService.putAbsenceById(absence);
	}

}
