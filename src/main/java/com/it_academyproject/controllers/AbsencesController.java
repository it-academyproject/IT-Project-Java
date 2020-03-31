package com.it_academyproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.tools.View;

@RestController
public class AbsencesController {
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	@Autowired
	AbsenceRepository myAbsenceRepository;
	
	//Edit and create Absences 
	@JsonView(View.SummaryWithOthers.class)
 	@PutMapping("api/students/absences")                    
	public Absence putAbsence(@RequestBody Absence absence){ 
		System.out.println("CHIVATO!");
		// student created to do the search
		MyAppUser student = new MyAppUser(); 
		// if student with id X exists...
		if (myAppUserRepository.existsById(student.getId())) { 
			//list created from absences repo of student with ID X
	        List<Absence> absencesList = myAbsenceRepository.findOneById(student.getId());

				absence.setDateMissing(absence.getDateMissing()); //HOW CAN I CATCH FROM JSON BODY???
				absence.setComment(absence.getComment()); ////HOW CAN I CATCH FROM JSON BODY???				
				
		        absencesList.add(absence);
		        
		     // save new absence edited in repo
		        myAbsenceRepository.save(absence); 
							
			return absence;

		} else {
			return null;
		}
		
	}
		
}
