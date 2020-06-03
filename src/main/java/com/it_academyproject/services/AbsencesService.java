package com.it_academyproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Student;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

@Service
public class AbsencesService
{
	@Autowired
	AbsenceRepository myAbsenceRepository;

	@Autowired
	MyAppUserRepository myAppUserRepository;

	// -------------------- -------------------- //
	
	public Absence createAbsence(Absence absence, Student userStudent)
	{
		Absence absenceCreated = new Absence();
		String studentId = userStudent.getId();
		userStudent = (Student) myAppUserRepository.findUserById(studentId);
		absenceCreated.setComment(absence.getComment());
		absenceCreated.setDateMissing(absence.getDateMissing());
		absenceCreated.setUserStudent(userStudent);
		myAbsenceRepository.save(absenceCreated);
		return absenceCreated;
	}

	public List<Absence> getAllAbsences()
	{
		return myAbsenceRepository.findAll();
	}

	public Absence getAbsenceById(Absence absence)
	{
		return myAbsenceRepository.findOneById(absence.getId());
	}

	public List<Absence> getAbsenceByStudentId(Student userStudent)
	{
		return myAbsenceRepository.findByUserStudentId(userStudent.getId());
	}
	
	//get all absence by student id
	public List<Absence> getAllAbsenceByStudentId(String userStudent){
		return myAbsenceRepository.findByUserStudentId(userStudent);
	}

	public Absence putAbsenceById(Absence absence)
	{
		if (!myAbsenceRepository.findAll().isEmpty())
		{
			Absence absenceToEdit = myAbsenceRepository.findOneById(absence.getId());
			if (absence.getDateMissing() != null && !absence.getDateMissing().toString().isEmpty())
			{
				absenceToEdit.setDateMissing(absence.getDateMissing());
			}
			if (absence.getComment() != null && !absence.getComment().isEmpty())
			{
				absenceToEdit.setComment(absence.getComment());
			}
			myAbsenceRepository.save(absenceToEdit);
			return absenceToEdit;
		} 
		else
		{
			return null;
		}
	}

	public List<Absence> findAbsenceByStudentId(Student userStudent, String studentId)
	{
		return myAbsenceRepository.findByUserStudentId(studentId);
	}

}