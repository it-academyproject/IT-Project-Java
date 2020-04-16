package com.it_academyproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

@Service
public class AbsencesService {

	@Autowired
	AbsenceRepository myAbsenceRepository;
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	//post absence
	public Absence createAbsence(Absence absence, MyAppUser userStudent) {
		
		String studentId = absence.getUserStudent().getId();
		Absence absenceCreated = new Absence();
		userStudent = myAppUserRepository.findOneById(studentId);

		absenceCreated.setComment(absence.getComment());
		absenceCreated.setDateMissing(absence.getDateMissing());
		absenceCreated.setUserStudent(userStudent);
		
		System.out.println("Student Id: " + studentId);
		System.out.println("Comment: " + absenceCreated.getComment());
		System.out.println("DateMissing: " + absenceCreated.getDateMissing());
		System.out.println("User: " + absenceCreated.getUserStudent());
		
		myAbsenceRepository.save(absenceCreated);
		return absenceCreated;
	}

	
//	public Absence createAbsence(Absence absence, String id) {
//	
//		Absence absenceCreated = new Absence();
//		MyAppUser userStudent = myAppUserRepository.findOneById(id);
//		
//		absenceCreated.setComment(absence.getComment());
//		absenceCreated.setDateMissing(absence.getDateMissing());
//		absenceCreated.setUserStudent(userStudent);
//		
//		System.out.println("Comment: " + absenceCreated.getComment());
//		System.out.println("DateMissing: " + absenceCreated.getDateMissing());
//		System.out.println("User: " + absenceCreated.getUserStudent());
//		System.out.println("Id: " + id);

//		myAbsenceRepository.save(absenceCreated);
//		return absenceCreated;
//	}

	//get all absences
	public List<Absence> getAllAbsences() {
		return myAbsenceRepository.findAll();
	}
	
	//get absence by id
	public Absence getAbsenceById(@RequestBody Absence absence) {
		return myAbsenceRepository.findOneById(absence.getId());
	}

	//put absence by id
	public Absence putAbsenceById(Absence absence) {
		
		// if AbsenceRepo is not empty...
		if (!myAbsenceRepository.findAll().isEmpty()) {

			Absence absenceToEdit = myAbsenceRepository.findOneById(absence.getId());

			String dateToEdit = absence.getDateMissing().toString();
			String commentToEdit = absence.getComment();

			if (commentToEdit.isEmpty() || commentToEdit.equals(null)) {
				absenceToEdit.setComment(absenceToEdit.getComment());
				System.out.println("sin cambios en comment");
			} else if (!commentToEdit.isEmpty()) {
				absenceToEdit.setComment(commentToEdit);
				System.out.println("Cambios en comment");
			}

			if (dateToEdit.isEmpty() || dateToEdit.equals(null)) {
				absenceToEdit.setDateMissing(absenceToEdit.getDateMissing());
				System.out.println("sin cambios en date");
			} else if (!dateToEdit.isEmpty()) {
				absenceToEdit.setDateMissing(absence.getDateMissing());
				System.out.println("Cambios en date");
			}

			// Comment and dateMissing fields can't quit of JSON Body and dateMissing can't
			// be empty becasue in any of that cases POSTMANthrows 500 internal server
			// error.

			myAbsenceRepository.save(absenceToEdit);
			return absenceToEdit;

		} else {
			return null;
		}	
	}
}
