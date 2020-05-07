package com.it_academyproject.repositories;

import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
	
	// by student id y date missing
	List<Absence> findByUserStudentAndDateMissing(Student userStudent, Date dateMissing);
	
	// by student id 
	List<Absence> findByUserStudentId(String userStudent);
	
	// by absence id
	Absence findOneById(Integer id);

}
