package com.it_academyproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Student;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.services.AbsencesService;
import com.it_academyproject.tools.View;

@RestController
public class AbsencesController
{
	@Autowired
	AbsenceRepository myAbsenceRepository;

	@Autowired
	AbsencesService absencesService;

	// -------------------- -------------------- //
	
	@PostMapping("api/absences") // --> Need to edit URI createAbsence & getAllAbsences have same URIs.
	public Absence createAbsence(@RequestBody Absence absence)
	{
		return absencesService.createAbsence(absence, absence.getUserStudent());
	}

	@JsonView(View.Summary.class)
	@GetMapping("api/absences")
	public List<Absence> getAllAbsences()
	{
		return absencesService.getAllAbsences();
	}

	@JsonView(View.Summary.class)
	@GetMapping("api/absences/id")
	public Absence getAbsenceById(@RequestBody Absence absence)
	{
		return absencesService.getAbsenceById(absence);
	}

	@JsonView(View.Summary.class)
	@GetMapping("api/absences/student/{student_id}")
	public List<Absence> getAbsenceByStudentId(@RequestBody Student userStudent,
			@PathVariable(name = "student_id") String studentId)
	{
		return absencesService.findAbsenceByStudentId(userStudent, studentId);
	}

	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("api/absences")
	public Absence putAbsence(@RequestBody Absence absence)
	{
		return absencesService.putAbsenceById(absence);
	}

}