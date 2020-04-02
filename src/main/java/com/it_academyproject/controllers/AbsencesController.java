package com.it_academyproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.tools.View;

@Service
@RestController
public class AbsencesController {

	@Autowired
	MyAppUserRepository myAppUserRepository;
	@Autowired
	AbsenceRepository myAbsenceRepository;
	
	// Call total absences
	@JsonView(View.Summary.class)
	@GetMapping("/api/students/absences")
	public List<Absence> getAllAbsences(Absence absence){
						
		return myAbsenceRepository.findAll();
		
	}
	
	// Call absence by id
	@JsonView(View.Summary.class)
	@GetMapping("/api/students/absences/id")
	public Absence getAbsenceById(@RequestBody Absence absence){
		return myAbsenceRepository.findOneById(absence.getId());
		
	}

	// Edit and create Absences
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("api/students/absences")
	public Absence putAbsence(@RequestBody @Valid Absence absence) {

		// if AbsenceRepo is not empty...
		if (!myAbsenceRepository.findAll().isEmpty()) { 
//			NEED TO FOCUS IN "COMMENT" AND "DATE" AND THAT'S ALL, NOW IS TURNING TO NULL ALL STUDENTS PARAMS :S
			
			absence.setDateMissing(absence.getDateMissing());
			absence.setComment(absence.getComment());
		
			myAbsenceRepository.save(absence);
			
			return absence;

		} else {
			return null;
		}

	}

}


//Absence absenceToEdit = myAbsenceRepository.findByAbsenceId(absence.getId());     
//absenceToEdit.setDateMissing(absence.getDateMissing()); 											// HOW CAN I CATCH FROM JSON BODY???
//absenceToEdit.setComment(absence.getComment()); 													// HOW CAN I CATCH FROM JSON BODY???
//
//// save new absence edited in repo
//myAbsenceRepository.save(absenceToEdit);
