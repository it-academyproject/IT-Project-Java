package com.it_academyproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.services.AbsencesService;
import com.it_academyproject.tools.View;

@RestController
public class AbsencesController {

	@Autowired
	AbsenceRepository myAbsenceRepository;
	@Autowired
	AbsencesService absencesService;

	// Create absence by student id
	@PostMapping("api/absences") // modificar URI
	public Absence createAbsence(@RequestBody Absence absence) {
		return absencesService.createAbsence(absence, absence.getUserStudent());
	}

	// Call total absences
	@JsonView(View.Summary.class)
	@GetMapping("api/absences")
	public List<Absence> getAllAbsences() {
		return absencesService.getAllAbsences();
	}

	// Call absence by id
	@JsonView(View.Summary.class)
	@GetMapping("api/absences/id")
	public Absence getAbsenceById(@RequestBody Absence absence) {
		return absencesService.getAbsenceById(absence);
	}

	// Call absences by student id
	@JsonView(View.Summary.class)
	@GetMapping("api/absences/student")
	public List<Absence> getAbsenceByStudentId(@RequestBody MyAppUser userStudent) {
		return absencesService.getAbsenceByStudentId(userStudent);
	}

	// Edit Absence by id
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("api/absences")
	public Absence putAbsence(@RequestBody Absence absence) {
		return absencesService.putAbsenceById(absence);
	}

}
