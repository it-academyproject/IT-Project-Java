package com.it_academyproject.repositories;

import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.MyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
	
//	ArrayList<Absence> findAllAbsences();

	// by student id y date missing
	List<Absence> findByUserStudentAndDateMissing(MyAppUser userStudent, Date dateMissing);
	
	// Find List by student id
	List<Absence> findListByUserStudent(MyAppUser userStudent);
	
	// Find Absence by user
    Absence findByUserStudent(MyAppUser userStudent);
	
	// by absence id
	Absence findOneById(Integer id);
	
	// by Date
//	Absence findOneByDate(Date date); // format to dd-mm-yyyy and check how to do the search
	
}	
	
	
	
//	// exist?
//	boolean existsByStudentId(String id);
//
//	//search in list by absence id
//	List<Absence> findOneById(int id);


