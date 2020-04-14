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
	public List<Absence> getAllAbsences(Absence absence) {

		return myAbsenceRepository.findAll();

	}

	// Call absence by id
	@JsonView(View.Summary.class)
	@GetMapping("/api/students/absences/id")
	public Absence getAbsenceById(@RequestBody Absence absence) {
		return myAbsenceRepository.findOneById(absence.getId());

	}

	// Edit Absence by id
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("api/students/absences")
	public Absence putAbsence(@RequestBody @Valid Absence absence) {

		// if AbsenceRepo is not empty...
		if (!myAbsenceRepository.findAll().isEmpty()) {

			Absence absenceToEdit = myAbsenceRepository.findOneById(absence.getId());

			if (absence.getDateMissing().toString().equalsIgnoreCase("")) {

				absenceToEdit.setComment(absence.getComment());

			} else if (absence.getComment().equalsIgnoreCase("")) {

				absenceToEdit.setDateMissing(absence.getDateMissing());

			} else {

				absenceToEdit.setComment(absence.getComment());
				absenceToEdit.setDateMissing(absence.getDateMissing());

			}

			myAbsenceRepository.save(absenceToEdit);

			return absenceToEdit;

		} else {
			return null;
		}
	}

}
