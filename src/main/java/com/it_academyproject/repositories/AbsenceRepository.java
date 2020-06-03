package com.it_academyproject.repositories;

import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer>
{
	List<Absence> findByUserStudentAndDateMissing(Student userStudent, Date dateMissing);

	List<Absence> findByUserStudentId(String userStudent);
<<<<<<< HEAD
=======
	
	// by absence id
	Absence findOneById(Integer id);
	
	
>>>>>>> 66828077c41e6a2ee61d164812ef001db6a6e283

	Absence findOneById(Integer id);
}